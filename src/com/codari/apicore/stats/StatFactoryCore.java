package com.codari.apicore.stats;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;

import com.codari.api5.stats.StatFactory;
import com.codari.api5.stats.StatHolder;
import com.codari.api5.stats.StatRegistrationException;
import com.codari.api5.stats.StatType;

public final class StatFactoryCore implements StatFactory {
	//-----Fields-----//
	private final Set<Class<? extends StatHolder>> finalizedStatHolders;
	private final Map<Class<? extends StatHolder>, Set<StatType>> registeredStatTypeSets;
	
	//-----Constructor-----//
	public StatFactoryCore() {
		this.finalizedStatHolders = new HashSet<>();
		this.registeredStatTypeSets = new HashMap<>();
	}
	
	//-----Public Methods-----//
	@Override
	public boolean isValidStatName(String name) {
		return StringUtils.isAlphaSpace(name);
	}
	
	@Override
	public void registerStatType(Class<? extends StatHolder> holder, StatType type) throws StatRegistrationException {
		Validate.notNull(type);
		if (this.finalizedStatHolders.contains(holder)) {
			throw new StatRegistrationException("Finalized holder: " + holder);
		}
		Set<StatType> registeredStatTypes = this.registeredStatTypeSets.get(holder);
		if (registeredStatTypes == null) {
			registeredStatTypes = new HashSet<>();
			registeredStatTypes.add(type);
			this.registeredStatTypeSets.put(holder, registeredStatTypes);
			return;
		}
		if (registeredStatTypes.contains(type)) {
			throw new StatRegistrationException("Duplicate stat name " + type + " for " + holder);
		}
		registeredStatTypes.add(type);
	}
	
	@Override
	public StatManagerCore createStatManager(StatHolder holder) {
		Class<? extends StatHolder> clazz = holder.getClass();
		Set<StatType> registeredStatTypes = this.registeredStatTypeSets.get(clazz);
		if (registeredStatTypes == null) {
			registeredStatTypes = new HashSet<>();
			this.registeredStatTypeSets.put(clazz, registeredStatTypes);
		}
		this.finalizedStatHolders.add(clazz);
		return new StatManagerCore(holder, registeredStatTypes);
	}
}