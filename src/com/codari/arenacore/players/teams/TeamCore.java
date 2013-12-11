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
	
	//-----Constructor-----//
	public TeamCore(String teamName, Combatant...combatants) {
		this.combatants = new ArrayList<>();
		this.teamName = teamName;
		for(Combatant combatant : combatants) {
			combatant.setTeam(this);
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
	
	public void setArena(Arena arena) {
		this.arena = arena;
	}
	
	@Override
	public String getTeamName() {
		return this.teamName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Team) {
			Team other = (Team) obj;
			return this.getTeamName().equals(other.getTeamName());
		}
		return false;
	}
	
	@Override
	public List<Combatant> getTeamMates(Combatant combatant) {
		List<Combatant> tempList = new ArrayList<>(this.combatants);
		if(this.combatants.contains(combatant)) {	//TODO - need to properly implement equals
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
	public int getTeamSize() {
		return this.combatants.size();
	}

	@Override
	public void setLeader(Combatant combatant) {
		combatant.setLeader(true);
	}

	@Override
	public boolean isLeader(Combatant combatant) {
		return combatant.checkIfLeader();
	}
}