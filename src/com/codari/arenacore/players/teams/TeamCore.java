package com.codari.arenacore.players.teams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

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
	public boolean equals(Team other) {	//Made by Mhenlo - check if better method available
		return this.getTeamName().equals(other.getTeamName());
	}
	
	@Override
	public List<Combatant> getTeamMates(Combatant combatant) {
		List<Combatant> tempList = new ArrayList<>(this.combatants);
		if(this.combatants.contains(combatant)) {
			tempList.remove(combatant);
		}
		return tempList;
	}
	
	@Override 
	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<>();
		for(Combatant combatant: combatants) {
			Player player = combatant.getPlayerReference().getPlayer();
			players.add(player);
		}
		return players;
	}
	
	@Override
	public void addToTeam(Combatant combatant) {
		combatant.setTeam(this);
		this.combatants.add(combatant);
	}
	
	@Override
	public void removeFromTeam(Combatant combatant) {
		combatant.setTeam(null);
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