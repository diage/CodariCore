package com.codari.apicore.stats;

import java.util.HashMap;
import java.util.Iterator;

import com.codari.api5.Codari;
import com.codari.api5.stats.Stat;
import com.codari.api5.stats.StatModifier;
import com.codari.api5.util.Modifier;
import com.codari.api5.util.SimpleModifier;

public final class StatCore extends Number implements Stat {
	private static final long serialVersionUID = -1444790023238197846L;
	//-----Fields-----//
	private final String name;
	private final StatManagerCore statManager;
	private final ModifierMap modifiers;
	private final float[] baseValues;
	private int level;
	
	//-----Constructor-----//
	public StatCore(String name, StatManagerCore statManager, float[] baseValues) {
		if (!Codari.INSTANCE.getStatFactory().isValidStatName(name)) {
			throw new IllegalArgumentException(name + " is not a valid stat name");
		}
		this.name = name;
		this.statManager = statManager;
		this.modifiers = new ModifierMap();
		this.baseValues = baseValues;
	}
	
	//-----Public Methods-----//
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public StatManagerCore getStatManager() {
		return this.statManager;
	}
	
	@Override
	public double doubleValue() {
		return this.floatValue();
	}

	@Override
	public float floatValue() {
		float baseValue = this.getBaseValue();
		float totalFixedValue = this.modifiers.getFixedValue();
		float totalPercentage = this.modifiers.getPercentage();
		float percentageIncrease = baseValue * totalPercentage;
		return baseValue + totalFixedValue + percentageIncrease;
	}

	@Override
	public int intValue() {
		return (int) this.floatValue();
	}

	@Override
	public long longValue() {
		return (long) this.floatValue();
	}
	
	@Override
	public float getBaseValue() {
		return this.baseValues[this.getLevel()];
	}
	
	@Override
	public int getLevel() {
		return this.level;
	}
	
	@Override
	public int getMaxLevel() {
		return this.baseValues.length - 1;
	}
	
	@Override
	public void setLevel(int level) {
		if (level > this.getMaxLevel()) {
			level = this.getMaxLevel();
		} else if (level < 0) {
			level = 0;
		}
		this.level = level;
	}
	
	@Override
	public void setModifier(String identifier, Modifier modifier) {
		if (modifier == null) {
			this.removeModifier(identifier);
		}
		StatModifier statModifier = new StatModifierCore(identifier, modifier);
		this.modifiers.put(identifier, statModifier);
	}
	
	@Override
	public StatModifier getModifier(String identifier) {
		return this.modifiers.get(identifier);
	}
	
	@Override
	public void removeModifier(String identifier) {
		this.modifiers.remove(identifier);
	}
	
	//-----Utility Methods-----//
	@Override
	public Iterator<StatModifier> iterator() {
		return new ModifierIterator();
	}

	@Override
	public int compareTo(Stat other) {
		return Float.compare(this.floatValue(), other.floatValue());
	}
	
	//-----Stat Modifier Core-----//
	private final class StatModifierCore extends SimpleModifier implements StatModifier {
		//-----Fields-----//
		private final String identifier;
		
		//-----Constructor-----//
		private StatModifierCore(String identifier, Modifier modifier) {
			super(modifier);
			this.identifier = identifier;
		}
		
		//-----Methods-----//
		@Override
		public String getIdentifier() {
			return this.identifier;
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
			return this.next();
		}

		@Override
		public void remove() {
			this.modifierIterator.remove();
			modifiers.subtract(this.next);
		}
	}
	
	//-----Modifier Map-----//
	@SuppressWarnings("serial")//I have no intention of serializing stat modifiers
	private final class ModifierMap extends HashMap<String, StatModifier> implements Modifier {
		//-----Fields-----//
		private float fixedValueTotal;
		private float percentageTotal;
		
		//-----Methods-----//
		@Override
		public StatModifier put(String key, StatModifier value) {
			StatModifier statModifier = super.put(key, value);
			if (statModifier == null) {
				this.add(value);
			} else {
				this.exchange(statModifier, value);
			}
			return statModifier;
		}
		
		@Override
		public StatModifier remove(Object key) {
			StatModifier statModifier = super.remove(key);
			if (statModifier != null) {
				this.subtract(statModifier);
			}
			return statModifier;
		}

		@Override
		public float getFixedValue() {
			return this.fixedValueTotal;
		}

		@Override
		public float getPercentage() {
			return this.percentageTotal;
		}
		
		private void add(Modifier modifier) {
			this.fixedValueTotal += modifier.getFixedValue();
			this.percentageTotal += modifier.getPercentage();
		}
		
		private void subtract(Modifier modifier) {
			this.fixedValueTotal -= modifier.getFixedValue();
			this.percentageTotal -= modifier.getPercentage();
		}
		
		private void exchange(Modifier oldModifier, Modifier newModifier) {
			this.fixedValueTotal += newModifier.getFixedValue() - oldModifier.getFixedValue();
			this.percentageTotal += newModifier.getPercentage() - oldModifier.getPercentage();
		}
	}
}