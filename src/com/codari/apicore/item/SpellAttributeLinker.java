package com.codari.apicore.item;

import java.util.HashMap;
import java.util.Map;

public class SpellAttributeLinker {
	private Map<String, Map<String, String>> links;
	
	public SpellAttributeLinker() {
		this.links = new HashMap<>();
	}
	
	
	public void setLinks(String spellName, Map<String, String> links) {
		this.links.put(spellName, links);
	}
	
	public void addLink(String spellName, String linkName, String attributeName) {
		if(this.links.containsKey(spellName)) {
			this.links.get(spellName).put(linkName, attributeName);
		} else {
			Map<String, String> spellLink = new HashMap<>();
			spellLink.put(linkName, attributeName);
			this.links.put(spellName, spellLink);
		}
	}
	
	public boolean hasLink(String spellName) {
		return this.links.containsKey(spellName);
	}
	
	public String getLink(String spellName, String linkName) {
		if(this.links.containsKey(spellName)) {
			return this.links.get(spellName).get(linkName);
		}
		return null;
	}
}
