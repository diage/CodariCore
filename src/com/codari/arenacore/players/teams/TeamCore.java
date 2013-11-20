package com.codari.arenacore.players.teams;

import java.util.ArrayList;
import java.util.List;

import com.codari.arena5.Arena;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.players.teams.TeamColor;

public class TeamCore implements Team {
	//-----Fields-----//
	protected final List<Combatant> combatants;
	private TeamColor teamColor;
	private Arena arena;
	
	//-----Constructor-----//
	public TeamCore(Combatant...combatants) {
		this.combatants = new ArrayList<>();
		for(Combatant combatant : combatants) {
			this.combatants.add(combatant);
		}
	}
	
	public TeamCore(List<Combatant> combatants) {
		this.combatants = combatants;
	}
	
	//-----Public Methods-----//
	@Override
	public TeamColor getColor() {
		return this.teamColor;
	}
	
	@Override
	public List<Combatant> combatants() {
		return new ArrayList<>(this.combatants);
	}
	
	@Override
	public Arena getArena() {
		return this.arena;
	}
	
	
	@Override
	public List<Combatant> getTeamMates(Combatant combatant) {
		List<Combatant> tempList = new ArrayList<>(this.combatants);
		if(this.combatants.contains(combatant)) {
			tempList.remove(combatant);
		}
		return tempList;
	}
	
	public void joinTeam(Combatant combatant) {
		this.combatants.add(combatant);
	}
}