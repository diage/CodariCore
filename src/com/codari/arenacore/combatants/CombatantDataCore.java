package com.codari.arenacore.combatants;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.codari.arena.players.combatants.CombatantStats;

public final class CombatantDataCore implements CombatantStats, ConfigurationSerializable {
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		//TODO
		return result;
	}
}