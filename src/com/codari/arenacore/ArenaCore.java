package com.codari.arenacore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codari.arena5.Arena;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.players.teams.TeamColor;
import com.codari.arenacore.players.teams.TeamCore;

public final class ArenaCore implements Arena {
	//-----Fields-----//
	private final String name;
	private final Map<TeamColor, Team> teams;
	private final List<ArenaTimer> timers;
	
	//-----Constructor-----//
	public ArenaCore(String name, List<ArenaTimer> timers) {
		this.name = name;
		this.teams = new HashMap<>();
		this.timers = timers;
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
		this.teams.put(TeamColor.RED, new TeamCore(this, TeamColor.RED, redTeam));
		this.teams.put(TeamColor.BLUE, new TeamCore(this, TeamColor.BLUE, blueTeam));
		
		this.timers = null;//TODO
	}
	
	//-----Public Methods-----//
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Map<TeamColor, Team> getTeams() {
		return teams;
	}
}