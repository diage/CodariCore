package com.codari.apicore.stats;

import java.util.HashMap;
import java.util.Iterator;

import com.codari.api5.stats.Stat;
import com.codari.api5.stats.StatModifier;
import com.codari.api5.stats.StatType;
import com.codari.api5.util.Modifier;
import com.codari.api5.util.SimpleModifier;

public final class StatCore implements Stat {
	//-----Fields-----//
	private final StatType type;
	private final StatManagerCore statManager;
	private final ModifierMap modifiers;
	private boolean pendingChanges;
	private float value;
	private float contingentValue;
	private int level;
	
	//-----Constructor-----//
	public StatCore(StatType type, StatManagerCore statManager, int level) {
		this.type = type;
		this.statManager = statManager;
		this.modifiers = new ModifierMap();
		this.setLevel(level);
	}
	
	public StatCore(StatType type, StatManagerCore statManager) {
		this(type, statManager, 0);
	}
	
	//-----Public Methods-----//
	@Override
	public StatType getType() {
		return this.type;
	}
	
	@Override
	public StatManagerCore getStatManager() {
		return this.statManager;
	}
	
	@Override
	public float value() {
		return this.value(true);
	}
	
	@Override
	public float value(boolean contingent) {
		this.calculate();
		return contingent ? this.contingentValue : this.value;
	}
	
	@Override
	public int getLevel() {
		return this.level;
	}
	
	@Override
	public void setLevel(int level) {
		this.level = level < 1 ? 1 : level > this.type.getMaxLevel() ? this.type.getMaxLevel() : level;
		this.handleChanges();
	}
	
	@Override
	public float getBaseValue() {
		return this.type.getBaseValue(this.level);
	}
	
	@Override
	public void setModifier(String identifier, Modifier modifier) {
		this.setModifier(identifier, modifier, false);
	}
	
	@Override
	public void setContingentModifier(String identifier, Modifier modifier) {
		this.setModifier(identifier, modifier, true);
	}
	
	@Override
	public StatModifier getModifier(String identifier) {
		return this.modifiers.get(identifier);
	}
	
	@Override
	public void removeModifier(String identifier) {
		this.modifiers.remove(identifier);
	}
	
	//-----Private Methods-----//
	private void setModifier(String identifier, Modifier modifier, boolean contingent) {
		if (modifier == null) {
			this.removeModifier(identifier);
		}
		StatModifier statModifier = new StatModifierCore(identifier, modifier, contingent);
		this.modifiers.put(identifier, statModifier);
	}
	
	private void calculate() {
		if (this.pendingChanges) {
			this.pendingChanges = false;
			float baseValue = this.getBaseValue();
			float percentageIncrease = baseValue * this.percentageTotal();
			float value = baseValue + percentageIncrease + this.fixedValueTotal();
			float contingentPercentageIncrease = this.value * this.contingentPercentageTotal();
			float contingentValue = value + contingentPercentageIncrease + this.contingentFixedValueTotal();
			this.value = value > this.type.getMaxValue() ? this.type.getMaxValue() :
				value < this.type.getMinValue() ? this.type.getMinValue() : value;
			this.contingentValue = contingentValue > this.type.getMaxValue() ? this.type.getMaxValue() :
				contingentValue < this.type.getMinValue() ? this.type.getMinValue() : contingentValue;
		}
	}
	
	private float percentageTotal() {
		return this.modifiers.percentageTotal;
	}
	
	private float fixedValueTotal() {
		return this.modifiers.fixedValueTotal;
	}
	
	private float contingentPercentageTotal() {
		return this.modifiers.contingentPercentageTotal;
	}
	
	private float contingentFixedValueTotal() {
		return this.modifiers.contingentFixedValueTotal;
	}
	
	private void handleChanges() {
		this.pendingChanges = true;
	}
	
	//-----Utility Methods-----//
	@Override
	public Iterator<StatModifier> iterator() {
		return new ModifierIterator();
	}

	@Override
	public int compareTo(Stat other) {
		return Float.compare(this.value(), other.value());
	}
	
	//-----Stat Modifier Core-----//
	private final class StatModifierCore extends SimpleModifier implements StatModifier {
		//-----Fields-----//
		private final String identifier;
		private final boolean contingent;
		
		//-----Constructor-----//
		private StatModifierCore(String identifier, Modifier modifier, boolean contingent) {
			super(modifier);
			this.identifier = identifier;
			this.contingent = contingent;
		}
		
		//-----Methods-----//
		@Override
		public String getIdentifier() {
			return this.identifier;
		}
		
		@Override
		public boolean isContingent() {
			return this.contingent;
		}
	}
	
	//-----Modifier Iterator-----//
	private final class ModifierIterator implements Iterator<StatModifier> {
		//-----Fields-----//
		private final Iterator<StatModifier> modifierIterator;
		private StatModifier next;
		
		//-----Constructor-----//
		private ModifierIterator() {
			this.modifierIterator = modifiers.values().iterator();
		}
		
		//-----Iterator Methods-----//
		@Override
		public boolean hasNext() {
			return this.modifierIterator.hasNext();
		}

		@Override
		public StatModifier next() {
			this.next = this.modifierIterator.next();
			return this.next;
		}

		@Override
		public void remove() {
			this.modifierIterator.remove();
			modifiers.remove(this.next);
		}
	}
	
	//-----Modifier Map-----//
	@SuppressWarnings("serial")//I have no intention of serializing stat modifiers
	private final class ModifierMap extends HashMap<String, StatModifier> {
		//-----Fields-----//
		private float fixedValueTotal, contingentFixedValueTotal;
		private float percentageTotal, contingentPercentageTotal;
		
		//-----Methods-----//
		@Override
		public StatModifier put(String key, StatModifier value) {
			StatModifier statModifier = super.put(key, value);
			if (statModifier == null) {
				this.add(value);
			} else {
				this.exchange(statModifier, value);
			}
			handleChanges();
			return statModifier;
		}
		
		@Override
		public StatModifier remove(Object key) {
			StatModifier statModifier = super.remove(key);
			if (statModifier != null) {
				this.remove(statModifier);
			}
			handleChanges();
			return statModifier;
		}
		
		private void add(StatModifier statModifier) {
			if (statModifier.isContingent()) {
				this.contingentFixedValueTotal += statModifier.getFixedValue();
				this.contingentPercentageTotal += statModifier.getPercentage();
			} else {
				this.fixedValueTotal += statModifier.getFixedValue();
				this.percentageTotal += statModifier.getPercentage();
			}
		}
		
		private void remove(StatModifier statModifier) {
			if (statModifier.isContingent()) {
				this.contingentFixedValueTotal -= statModifier.getFixedValue();
				this.contingentPercentageTotal -= statModifier.getPercentage();
			} else {
				this.fixedValueTotal -= statModifier.getFixedValue();
				this.percentageTotal -= statModifier.getPercentage();
			}
		}
		
		private void exchange(StatModifier oldStatModifier, StatModifier newStatModifier) {
			this.remove(oldStatModifier);
			this.add(newStatModifier);
		}
	}
}