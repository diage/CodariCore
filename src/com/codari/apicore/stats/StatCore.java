package com.codari.apicore.stats;

import java.util.HashMap;
import java.util.Iterator;

import com.codari.api.Codari;
import com.codari.api.stats.Stat;
import com.codari.api.stats.StatModifier;
import com.codari.api.util.Modifier;
import com.codari.api.util.ModifierUtils;

public final class StatCore extends Number implements Stat {
	private static final long serialVersionUID = -1444790023238197846L;
	//-----Fields-----//
	private final String name;
	private final StatManagerCore statManager;
	private final ModifierMap modifiers;
	private float baseValue;
	
	//-----Constructor-----//
	public StatCore(String name, StatManagerCore statManager, float baseValue) {
		if (!Codari.INSTANCE.getStatFactory().isValidStatName(name)) {
			throw new IllegalArgumentException(name + " is not a valid stat name");
		}
		this.name = name;
		this.statManager = statManager;
		this.modifiers = new ModifierMap();
		this.baseValue = baseValue;
	}
	
	public StatCore(String name, StatManagerCore statManager) {
		this(name, statManager, 0);
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
		float totalFixedValue = this.modifiers.total.getFixedValue();
		float totalPercentage = this.modifiers.total.getPercentage();
		float percentageIncrease = this.baseValue * totalPercentage;
		return this.baseValue + totalFixedValue + percentageIncrease;
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
		return this.baseValue;
	}
	
	@Override
	public void setBaseValue(float baseValue) {
		this.baseValue = baseValue;
	}
	
	@Override
	public void increaseBaseValue(float amount) {
		this.setBaseValue(this.getBaseValue() + amount);
	}
	
	@Override
	public void decreaseBaseValue(float amount) {
		this.setBaseValue(this.getBaseValue() - amount);
	}
	
	@Override
	public boolean addModifier(String identifier, Modifier modifier) {
		if (this.modifiers.containsKey(identifier)) {
			return false;
		}
		StatModifier statModifier = new StatModifierCore(identifier, modifier);
		this.modifiers.put(identifier, statModifier);
		return true;
	}
	
	@Override
	public StatModifier getModifier(String identifier) {
		return this.modifiers.get(identifier);
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
	
	//-----Stat Modifier-----//
	private final class StatModifierCore implements StatModifier {
		//-----Fields-----//
		private final String identifier;
		private Modifier modifier;
		
		//-----Constructor-----//
		private StatModifierCore(String identifier, Modifier modifier) {
			this.identifier = identifier;
			this.modifier = ModifierUtils.handleNullModifier(modifier);
		}
		
		//-----Methods-----//
		@Override
		public float getFixedValue() {
			return this.modifier.getFixedValue();
		}
		
		@Override
		public float getPercentage() {
			return this.modifier.getPercentage();
		}
		
		@Override
		public String getIdentifier() {
			return this.identifier;
		}
		
		@Override
		public void set(Modifier modifier) {
			modifier = ModifierUtils.handleNullModifier(modifier);
			modifiers.total.exchange(this.modifier, modifier);
			this.modifier = modifier;
		}
		
		@Override
		public void remove() {
			modifiers.remove(this.identifier);
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
			if (this.next == null) {
				throw new IllegalStateException();
			}
			this.modifierIterator.remove();
			modifiers.total.subtract(this.next);
			this.next = null;
		}
	}
	
	//-----Modifier Map-----//
	@SuppressWarnings("serial")//I have no intention of serializing stat modifiers
	private final class ModifierMap extends HashMap<String, StatModifier> {
		//-----Fields-----//
		private final ModifierTotal total;
		
		//-----Constructor-----//
		private ModifierMap() {
			super();
			this.total = new ModifierTotal();
		}
		
		//-----Methods-----//
		@Override
		public StatModifier put(String key, StatModifier value) {
			StatModifier statModifier = super.put(key, value);
			if (statModifier == null) {
				this.total.add(value);
			} else {
				this.total.exchange(statModifier, value);
			}
			return statModifier;
		}
		
		@Override
		public StatModifier remove(Object key) {
			StatModifier statModifier = super.remove(key);
			if (statModifier != null) {
				this.total.subtract(statModifier);
			}
			return statModifier;
		}
	}
	
	//-----Total Modifier-----//
	private final class ModifierTotal implements Modifier {
		//-----Fields-----//
		private float fixedValueTotal;
		private float percentageTotal;
		
		//-----Public Methods-----//
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