package com.codari.apicore.stats;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.codari.api5.stats.StatFactory;
import com.codari.api5.stats.StatHolder;

public final class StatFactoryCore implements StatFactory {
	//-----Fields-----//
	private final Set<Class<? extends StatHolder>> finalizedStatHolders;
	private final Map<Class<? extends StatHolder>, Set<String>> registeredStatNames;
	
	//-----Constructor-----//
	public StatFactoryCore() {
		this.finalizedStatHolders = new HashSet<>();
		this.registeredStatNames = new HashMap<>();
	}
	
	//-----Public Methods-----//
	@Override
	public boolean isValidStatName(String name) {
		return StringUtils.isAlphaSpace(name);
	}
}