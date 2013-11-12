package com.codari.arenacore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.codari.api.util.PlayerReference;
import com.codari.arena.Arena;
import com.codari.arena.ArenaBuilder;
import com.codari.arena.ArenaManager;
import com.codari.arena.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.combatants.CombatantDataCore;

public class ArenaManagerCore implements ArenaManager {
	//-----Fields-----//
	private final Map<String, Combatant> combatants;
	private final Map<String, ArenaCore> arenas;
	
	//-----Constructor-----//
	public ArenaManagerCore() {
		this.combatants = new HashMap<>();
		this.arenas = new HashMap<>();
		ConfigurationSerialization.registerClass(CombatantDataCore.class);
	}
	
	//-----Public Methods-----//
	@Override
	public Combatant getCombatant(String name) {
		Combatant combatant = this.combatants.get(name);
		if (combatant == null) {
			combatant = new CombatantCore(PlayerReference.instanceOf(name));
			this.combatants.put(name, combatant);
		}
		return combatant;
	}
	
	@Override
	public Combatant getCombatant(OfflinePlayer player) {
		return this.getCombatant(player.getName());
	}
	
	@Override
	public Combatant getCombatant(PlayerReference playerReference) {
		return this.getCombatant(playerReference.getName());
	}
	
	@Override
	public ArenaCore getArena(String name) {
		return this.arenas.get(name);
	}

	@Override
	public Arena buildArena(ArenaBuilder arenaBuilder, String requestedName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArenaBuilder getArenaBuider() {
		// TODO Auto-generated method stub
		return null;
	}
}