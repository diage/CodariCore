package com.codari.apicore.stats;

import org.apache.commons.lang.StringUtils;

import com.codari.api5.stats.StatFactory;

public final class StatFactoryCore implements StatFactory {
	//-----Fields-----//
	
	//-----Constructor-----//
	public StatFactoryCore() {
		
	}
	
	//-----Public Methods-----//
	@Override
	public boolean isValidStatName(String name) {
		return StringUtils.isAlphaSpace(name);
	}
}