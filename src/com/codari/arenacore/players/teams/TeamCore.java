package com.codari.arenacore.players.teams;

import java.util.ArrayList;
import java.util.List;

import com.codari.arena.Arena;
import com.codari.arena.players.combatants.Combatant;
import com.codari.arena.players.teams.Team;
import com.codari.arena.players.teams.TeamColor;

public class TeamCore implements Team {
	//-----Fields-----//
	protected final List<Combatant> members;
	
	//-----Constructor-----//
	public TeamCore() {
		this.members = new ArrayList<>();
	}
	
	//-----Public Methods-----//
	@Override
	public TeamColor getColor() {
		return null;
	}
	
	@Override
	public List<Combatant> combatants() {
		return null;
	}
	
	@Override
	public Arena getArena() {
		return null;
	}
	
	@Override
	public List<Combatant> getTeamMates(Combatant combatant) {
		return null;
	}
}