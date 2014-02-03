package com.codari.apicore.attribute;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.util.org.apache.commons.lang3.builder.HashCodeBuilder;

import com.codari.api5.attribute.Attribute;
import com.codari.api5.attribute.modifier.AttributeModifier;

public final class AttributeCore implements Attribute {
	//-----Fields-----//
	private final AttributeTemplateCore template;
	private final Map<String, AttributeModifier> modifiers;
	private final Set<AttributeModifier> inconsistentModifiers;
	
	private int level;
	private double consistentModifierTotal;
	
	private final int hash;
	
	//-----Constructor-----//
	public AttributeCore(AttributeTemplateCore template) {
		this.template = template;
		this.modifiers = new LinkedHashMap<>();
		this.inconsistentModifiers = new LinkedHashSet<>();
		this.hash = new HashCodeBuilder()
				.append(this.getName().toLowerCase())
				.build();
	}
	
	//-----Methods-----//
	@Override
	public AttributeTemplateCore getTemplate() {
		return this.template;
	}
	
	@Override
	public String getId() {
		return this.template.getId();
	}
	
	@Override
	public String getName() {
		return this.template.getName();
	}
	
	@Override
	public String getDescription() {
		return this.template.getDescription();
	}
	
	@Override
	public int getLevel() {
		return this.level;
	}
	
	@Override
	public int getMaxLevel() {
		return this.template.getMaxLevel();
	}
	
	@Override
	public void setLevel(int level) {
		if (level < 0) {
			level = 0;
		} else if (level > this.template.getMaxLevel()) {
			level = this.template.getMaxLevel();
		}
		this.level = level;
	}
	
	@Override
	public double getBaseValue() {
		return this.template.getBaseValue(this.level);
	}
	
	@Override
	public double getScalarValue() {
		double scalarValue = this.getBaseValue() + this.consistentModifierTotal;
		if (scalarValue > this.template.getMaxValue()) {
			scalarValue = this.template.getMaxValue();
		} else if (scalarValue < this.template.getMinValue()) {
			scalarValue = this.template.getMinValue();
		}
		return scalarValue;
	}
	
	@Override
	public double getValue() {
		double baseValue = this.getBaseValue();
		double value = baseValue + this.consistentModifierTotal;
		for (AttributeModifier inconsistentModifier : this.inconsistentModifiers) {
			value += inconsistentModifier.getAppliedValue(baseValue);
		}
		if (value > this.template.getMaxValue()) {
			value = this.template.getMaxValue();
		} else if (value < this.template.getMinValue()) {
			value = this.template.getMinValue();
		}
		return value;
	}
	
	@Override
	public AttributeModifier applyModifier(AttributeModifier modifier, boolean forceConsistent) {
		if (modifier == null) {
			throw new IllegalArgumentException("null modifier");
		}
		if (forceConsistent) {
			modifier = modifier.makeConsistent();
		}
		AttributeModifier replacedModifier = this.modifiers.put(modifier.getId(), modifier);
		if (replacedModifier != null) {
			this.handleModifierRemoval(replacedModifier);
		}
		this.handleModifierApplication(modifier);
		return replacedModifier;
	}
	
	@Override
	public AttributeModifier applyModifier(AttributeModifier modifier) {
		return this.applyModifier(modifier, false);
	}
	
	@Override
	public AttributeModifier getModifier(String modifierId) {
		return this.modifiers.get(modifierId);
	}
	
	@Override
	public AttributeModifier removeModifier(String modifierId) {
		AttributeModifier removedModifier = this.modifiers.remove(modifierId);
		if (removedModifier != null) {
			this.handleModifierRemoval(removedModifier);
		}
		return removedModifier;
	}
	
	//-----Helper Methods-----//
	private void handleModifierApplication(AttributeModifier modifier) {
		if (modifier.isConsistent()) {
			this.consistentModifierTotal += modifier.getAppliedValue(this.getBaseValue());
		} else {
			this.inconsistentModifiers.add(modifier);
		}
	}
	
	private void handleModifierRemoval(AttributeModifier modifier) {
		if (modifier.isConsistent()) {
			this.consistentModifierTotal -= modifier.getAppliedValue(this.getBaseValue());
		} else {
			this.inconsistentModifiers.remove(modifier);
		}
		if (this.modifiers.size() == this.inconsistentModifiers.size()) {
			this.consistentModifierTotal = 0;
		}
	}
	
	//-----Utility Methods-----//
	@Override
	public int compareTo(Attribute other) {
		return this.getName().compareToIgnoreCase(other.getName());
	}
	
	@Override
	public StatModifierIterator iterator() {
		return new StatModifierIterator();
	}
	
	@Override
	public int hashCode() {
		return this.hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof AttributeCore) {
			return this.getName().equalsIgnoreCase(((AttributeCore) obj).getName());
		}
		return false;
	}
	
	//-----Modifier Iterator-----//
	public final class StatModifierIterator implements Iterator<AttributeModifier> {
		//-----Fields-----//
		private final Iterator<Entry<String, AttributeModifier>> internalIterator;
		private AttributeModifier next;
		
		//-----Constructor-----//
		private StatModifierIterator() {
			this.internalIterator = modifiers.entrySet().iterator();
		}
		
		//-----Methods-----//
		@Override
		public boolean hasNext() {
			return this.internalIterator.hasNext();
		}
		
		@Override
		public AttributeModifier next() {
			this.next = this.internalIterator.next().getValue();
			return next;
		}
		
		@Override
		public void remove() {
			this.internalIterator.remove();
			handleModifierRemoval(this.next);
		}
	}
}
