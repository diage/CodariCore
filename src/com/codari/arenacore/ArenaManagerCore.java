package com.codari.arenacore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.codari.api5.util.PlayerReference;
import com.codari.arena5.Arena;
import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.ArenaManager;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.players.teams.TeamColor;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.combatants.CombatantDataCore;

public class ArenaManagerCore implements ArenaManager {
	//-----Fields-----//
	private final Map<String, Combatant> combatants;
	private final Map<String, ArenaCore> arenas;
	
	//TODO
	private Arena onlyArena = null;
	
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
		
		return null;
	}

	@Override
	public ArenaBuilder getArenaBuider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team getTeam(String arenaName, TeamColor teamColor) {
		return this.onlyArena.getTeams().get(teamColor);
	}

	@Override
	public Team getTeam(Combatant combatant) {
		if(this.onlyArena.getTeams().get(TeamColor.RED).getTeamMates(combatant).size() > 0) {
			return this.onlyArena.getTeams().get(TeamColor.RED);
		} else if (this.onlyArena.getTeams().get(TeamColor.BLUE).getTeamMates(combatant).size() > 0){
			return this.onlyArena.getTeams().get(TeamColor.BLUE);
		}
		return null;
	}
	
	public void tempBuildArena(Combatant...combatants) {
		this.onlyArena = new ArenaCore("NEVER MORE THAN ONE ARENA!!", combatants);
	}
}