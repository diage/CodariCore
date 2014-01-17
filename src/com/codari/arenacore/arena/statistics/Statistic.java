package com.codari.arenacore.arena.statistics;

import com.codari.api5.Codari;
import com.codari.api5.attribute.Attribute;
import com.codari.api5.attribute.modifier.AttributeModifier;
import com.codari.api5.attribute.modifier.ModifierValue;

public class Statistic {
	//-----Constants-----//
	private static final String ID_PREFIX = "STATISTIC_";
	
	private final Attribute attribute;
	
	public Statistic(String statisticID, String name, String description) {
		this.attribute = Codari.getAttributeFactory()
				.templateBuilder(ID_PREFIX + statisticID)
				.setName(name)
				.setDescription(description)
				.setMinValue(0)
				.build().buildAttribute();
	}
	
	public String getID() {
		return this.attribute.getId();
	}
	
	public String getName() {
		return this.attribute.getName();
	}
	
	public String getDescription() {
		return this.attribute.getDescription();
	}
	
	public double getValue() {
		return this.attribute.getValue();
	}
	
	public void incrementStatistic(double amount) {
	}
	
	public void setStatistic(double amount) {
		
	}
	
	@SuppressWarnings("unused")
	private final class StatisticModifier {
		private AttributeModifier modifier;
		
		private final class StatisticModifierValue implements ModifierValue {

			@Override
			public double getValue() {
				// TODO Auto-generated method stub
				return 0;
			}
			
		}
	}
}
