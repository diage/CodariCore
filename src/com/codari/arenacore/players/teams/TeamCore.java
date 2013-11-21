package com.codari.arenacore.players.teams;

import java.util.ArrayList;
import java.util.List;

import com.codari.arena5.Arena;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.players.teams.TeamColor;
import com.codari.arenacore.ArenaCore;

public class TeamCore implements Team {
	//-----Fields-----//
	protected final List<Combatant> combatants;
	private Arena arena;
	private String teamName;
	private boolean isLeader;
	
	//-----Constructor-----//
	public TeamCore(String teamName, Combatant...combatants) {
		this.combatants = new ArrayList<>();
		this.teamName = teamName;
		for(Combatant combatant : combatants) {
			this.combatants.add(combatant);
		}
	}
	
	public TeamCore(List<Combatant> combatants) {
		this.combatants = combatants;
	}
	
	@Deprecated
	public TeamCore(ArenaCore arenaCore, TeamColor blue, List<Combatant> blueTeam) {
		this.combatants = null;
	}

	//-----Public Methods-----//	
	@Override
	public List<Combatant> combatants() {
		return new ArrayList<>(this.combatants);
	}
	
	@Override
	public Arena getArena() {
		return this.arena;
	}
	
	@Override
	public String getTeamName() {
		return this.teamName;
	}
	
	
	@Override
	public List<Combatant> getTeamMates(Combatant combatant) {
		List<Combatant> tempList = new ArrayList<>(this.combatants);
		if(this.combatants.contains(combatant)) {
			tempList.remove(combatant);
		}
		return tempList;
	}
	
	public void addToTeam(Combatant combatant) {
		this.combatants.add(combatant);
	}
	
	public void removeFromTeam(Combatant combatant) {
		this.combatants.remove(combatant);
	}

	@Override
	public void setLeader(Combatant combatant) {
		this.isLeader = true;
	}

	@Override
	public boolean isLeader(Combatant combatant) {
		return this.isLeader;
	}
}