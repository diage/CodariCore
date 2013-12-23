package com.codari.arenacore.players.combatants;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.codari.arena5.players.combatants.CombatantStats;

public final class CombatantDataCore implements CombatantStats, ConfigurationSerializable {
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		//FIXME - Soren
		return result;
	}
}