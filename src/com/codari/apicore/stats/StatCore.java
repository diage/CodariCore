package com.codari.apicore.stats;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.codari.api5.Codari;
import com.codari.api5.stats.Stat;
import com.codari.api5.stats.StatModifier;
import com.codari.api5.util.Modifier;
import com.codari.api5.util.ModifierUtils;

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
		float totalFixedValue = this.modifiers.getFixedValue();
		float totalPercentage = this.modifiers.getPercentage();
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
	public StatModifier setModifier(String identifier, Modifier modifier) {
		if (modifier == null) {
			return this.removeModifier(identifier);
		}
		StatModifier statModifier = new StatModifierCore(identifier, modifier);
		this.modifiers.put(identifier, statModifier);
		return statModifier;
	}
	
	@Override
	public StatModifier getModifier(String identifier) {
		return this.modifiers.get(identifier);
	}
	
	@Override
	public StatModifier removeModifier(String identifier) {
		return this.modifiers.remove(identifier);
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
	private final class StatModifierCore implements StatModifier {
		//-----Fields-----//
		private final String identifier;
		private boolean active;
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
		public boolean isActive() {
			return this.active;
		}
		
		@Override
		public void set(Modifier modifier) {
			if (this.isActive()) {
				modifiers.exchange(this.modifier, modifier);
				this.modifier = ModifierUtils.handleNullModifier(modifier);
			}
		}
		
		@Override
		public void remove() {
			if (this.isActive()) {
				modifiers.remove(this.identifier);
			}
		}
	}
	
	//-----Modifier Iterator-----//
	private final class ModifierIterator implements Iterator<StatModifier> {
		//-----Fields-----//
		private final Iterator<StatModifier> modifierIterator;
		private StatModifier next;
		
		//-----Constructor-----//
		private ModifierIterator() {
			this.modifierIterator = new HashSet<StatModifier>(modifiers.values()).iterator();
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
			if (this.next == null) {
				throw new IllegalStateException();
			}
			modifiers.remove(this.next.getIdentifier());
			this.next = null;
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
			StatModifier statModifier = super.get(key);
			if (statModifier != null) {
				statModifier.set(value);
				return statModifier;
			} else {
				statModifier = new StatModifierCore(key, value);
				super.put(key, statModifier);
				this.add(value);
				return null;
			}
		}
		
		@Override
		public StatModifier remove(Object key) {
			StatModifier statModifier = super.remove(key);
			if (statModifier != null) {
				this.subtract(statModifier);
				((StatModifierCore) statModifier).active = false;
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