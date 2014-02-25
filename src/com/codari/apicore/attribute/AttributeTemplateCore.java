package com.codari.apicore.attribute;

import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import net.minecraft.util.org.apache.commons.lang3.builder.HashCodeBuilder;

import com.codari.api5.attribute.AttributeTemplate;
import com.codari.api5.exceptions.InvalidAttributeException;
import com.codari.apicore.CodariCore;

public final class AttributeTemplateCore implements AttributeTemplate {
	//-----Fields-----//
	private final String attributeId;
	private String name;
	private String description;
	private final double[] baseValues;
	private final double minValue, maxValue;
	private final int hash;
	
	//-----Constructor-----//
	private AttributeTemplateCore(TemplateBuilderCore builder) {
		builder.validate();
		this.attributeId = builder.attributeId;
		this.name = builder.name;
		this.description = StringUtils.trimToEmpty(builder.description);
		this.baseValues = ArrayUtils.clone(builder.baseValues);
		this.minValue = builder.minValue;
		this.maxValue = builder.maxValue;
		this.hash = new HashCodeBuilder()
				.append(this.attributeId)
				.build();
	}
	
	//-----Methods-----//
	@Override
	public String getId() {
		return this.attributeId;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public double[] getBaseValues() {
		return ArrayUtils.clone(this.baseValues);
	}
	
	@Override
	public double getBaseValue(int level) {
		return this.baseValues[level];
	}
	
	@Override
	public int getMaxLevel() {
		return this.baseValues.length - 1;
	}
	
	@Override
	public double getMinValue() {
		return this.minValue;
	}
	
	@Override
	public double getMaxValue() {
		return this.maxValue;
	}
	
	@Override
	public AttributeCore buildAttribute() {
		return new AttributeCore(this);
	}
	
	//-----Utility Methods-----//
	public int hashCode() {
		return this.hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof AttributeTemplateCore) {
			return this.attributeId.equals(((AttributeTemplateCore) obj).attributeId);
		}
		return false;
	}
	
	//-----Stat Builder-----//
	public final static class TemplateBuilderCore implements AttributeTemplateBuilder {
		//-----Fields-----//
		private final String attributeId;
		private String name;
		private String description;
		private double[] baseValues;
		private double minValue, maxValue;
		
		//-----Constructor-----//
		public TemplateBuilderCore(String statId) {
			if (CodariCore.instance().getAttributeFactory().isRegistered(statId)) {
				throw new IllegalArgumentException("The stat id \"" + statId +
						"\" has already been registered to a stat template");
			}
			this.attributeId = statId;
			this.baseValues = new double[]{0};
			this.maxValue = Long.MIN_VALUE;
			this.maxValue = Long.MAX_VALUE;
		}
		
		//-----Methods-----//
		@Override
		public AttributeTemplateCore build() {
			AttributeTemplateCore statTemplate = new AttributeTemplateCore(this);
			CodariCore.instance().getAttributeFactory().registerStatTemplate(statTemplate);
			return statTemplate;
		}
		
		private void validate() {
			if (!CodariCore.instance().getAttributeFactory().isValidStatName(this.name)) {
				throw new InvalidAttributeException("Invalid name \"" + this.name + "\"");
			}
		}
		
		@Override
		public AttributeTemplateBuilder setName(String displayName) {
			this.name = displayName;
			return this;
		}
		
		@Override
		public AttributeTemplateBuilder setDescription(String description) {
			this.description = description;
			return this;
		}
		
		@Override
		public AttributeTemplateBuilder setBaseValues(double[] baseValues) {
			this.baseValues = ArrayUtils.isEmpty(baseValues) ? new double[]{0} : baseValues;
			return this;
		}
		
		@Override
		public AttributeTemplateBuilder setMinValue(double minValue) {
			this.minValue = minValue;
			return this;
		}
		
		@Override
		public AttributeTemplateBuilder setMaxValue(double maxValue) {
			this.maxValue = maxValue;
			return this;
		}
	}
}
