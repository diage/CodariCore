package com.codari.apicore.attribute;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.codari.api5.attribute.AttributeFactory;
import com.codari.api5.attribute.AttributeTemplate;
import com.codari.api5.attribute.InvalidAttributeException;
import com.codari.apicore.attribute.AttributeTemplateCore.TemplateBuilderCore;

public final class AttributeFactoryCore implements AttributeFactory {
	//-----Fields-----//
	private final Map<String, AttributeTemplateCore> attributeTemplates;
	
	//-----Constructor-----//
	public AttributeFactoryCore() {
		this.attributeTemplates = new HashMap<>();
	}
	
	//-----Methods-----//
	@Override
	public TemplateBuilderCore templateBuilder(String statId) {
		return new TemplateBuilderCore(statId);
	}
	
	@Override
	public AttributeCore buildAttribute(String attributeId) {
		if (!this.isRegistered(attributeId)) {
			throw new InvalidAttributeException("No attribute template registered under the id " + attributeId);
		}
		return this.getTemplate(attributeId).buildAttribute();
	}
	
	void registerStatTemplate(AttributeTemplateCore attributeTemplate) {
		this.attributeTemplates.put(attributeTemplate.getId(), attributeTemplate);
	}
	
	@Override
	public AttributeTemplateCore getTemplate(String attributeId) {
		return this.attributeTemplates.get(attributeId);
	}
	
	@Override
	public AttributeTemplateCore[] getTemplates(String... attributeIds) {
		int length = attributeIds != null ? attributeIds.length : 0;
		AttributeTemplateCore[] attributeTemplates = new AttributeTemplateCore[length];
		for (int i = 0; i < length; i++) {
			attributeTemplates[i] = this.getTemplate(attributeIds[i]);
		}
		return attributeTemplates;
	}
	
	@Override
	public boolean isRegistered(String attributeId) {
		return this.attributeTemplates.containsKey(attributeId);
	}
	
	@Override
	public boolean isRegistered(String... attributeIds) {
		for (String attributeId : attributeIds) {
			if (!this.isRegistered(attributeId)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean isValidStatName(String name) {
		return StringUtils.isNotBlank(name) && StringUtils.isAlphaSpace(name);
	}
	
	//-----Utility Methods-----//
	@Override
	public StatTemplateIterator iterator() {
		return new StatTemplateIterator();
	}
	
	//-----Stat Template Iterator-----//
	public class StatTemplateIterator implements Iterator<AttributeTemplate> {
		//-----Fields-----//
		private final Iterator<Entry<String, AttributeTemplateCore>> internalIterator;
		
		//-----Constructor-----//
		private StatTemplateIterator() {
			this.internalIterator = attributeTemplates.entrySet().iterator();
		}
		
		//-----Methods-----//
		@Override
		public boolean hasNext() {
			return this.internalIterator.hasNext();
		}
		
		@Override
		public AttributeTemplateCore next() {
			return this.internalIterator.next().getValue();
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
