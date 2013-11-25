package com.codari.arenacore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codari.arena5.Arena;
import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;


public final class ArenaCore implements Arena {
	//-----Fields-----//
	private final String name;
	private final ArenaBuilderCore builder;
	private final Map<String, Team> teams;
	
	//-----Constructors-----//
	public ArenaCore(String name, ArenaBuilderCore builder) {
		this.name = name;
		this.builder = builder;
		this.teams = new HashMap<>();
	}
	
	public ArenaCore(String name, Combatant...combatants) {
		int totalPlayers = combatants.length;
		List<Combatant> redTeam, blueTeam;
		redTeam = new ArrayList<>();
		blueTeam = new ArrayList<>();
		this.teams = new HashMap<>();
		this.name = name;
		for(; totalPlayers > (combatants.length / 2); totalPlayers-- ) {
			redTeam.add(combatants[totalPlayers - 1]);
		}
		for(; totalPlayers > 0; totalPlayers--) {
			blueTeam.add(combatants[totalPlayers - 1]);
		}
//		this.teams.put(TeamColor.RED, new TeamCore(this, TeamColor.RED, redTeam));
//		this.teams.put(TeamColor.BLUE, new TeamCore(this, TeamColor.BLUE, blueTeam));
		
		this.builder = null;
	}
	
	//-----Public Methods-----//
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Map<String, Team> getTeams() {
		return this.teams;
	}
	
	@Override
	public ArenaBuilder getArenaBuilder() {
		return this.builder;
	}
}