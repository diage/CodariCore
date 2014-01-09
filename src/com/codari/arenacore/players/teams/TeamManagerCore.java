package com.codari.arenacore.players.teams;

import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.players.teams.Team;
import com.codari.arena5.players.teams.TeamManager;

public class TeamManagerCore implements TeamManager {
	private Map<String, TeamCore> teams;
	
	public TeamManagerCore() {
		this.teams = new HashMap<>();
	}
	
	@Override
	public boolean putTeam(String teamName, Team team) {
		if(!this.teams.containsKey(teamName)) {
			this.teams.put(teamName, (TeamCore)team);
			return true;
		}
		return false;
	}
	
	@Override
	public void removeTeam(String teamName) {
		if(this.teams.containsKey(teamName)) {
			this.teams.remove(teamName);
		}
	}

	@Override
	public Team getTeam(String teamName) {
		if(this.teams.containsKey(teamName)) {
			return this.teams.get(teamName);
		}
		return null;
	}

	@Override
	public boolean containsTeam(String teamName) {
		if(this.teams.containsKey(teamName)) {
			return true;
		}
		return false;
	}
}
