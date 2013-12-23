package com.codari.arenacore.players.teams;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.codari.arena5.arena.Arena;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.players.teams.TeamColor;
import com.codari.arenacore.arena.ArenaCore;

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
	
	public boolean setArena(Arena arena) {
		if(arena == null) {
			this.leaveArena();
			return false;
		}
		this.arena = arena;
		for(Combatant combatant : this.combatants) {
			if(!combatant.sendToArena(arena)) {
				Bukkit.broadcastMessage(ChatColor.RED + "ERROR: Failed to add " 
						+ combatant.getPlayerReference().getName() + " to the arena!");
				return false;
			}
		}
		return true;
		
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
	public int hashCode() {
		return new HashCodeBuilder().append(this.getTeamName()).toHashCode();
	}
	
	@Override
	public List<Combatant> getTeamMates(Combatant combatant) {
		List<Combatant> tempList = new ArrayList<>(this.combatants);
		if(this.combatants.contains(combatant)) {	//FIXME - need to properly implement equals (combatant)
			tempList.remove(combatant);			
		}
		return tempList;
	}
	
	@Override 
	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<>();
		for(Combatant combatant: combatants) {
			Player player = combatant.getPlayer();
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
	
	public void leaveArena() {
		this.arena = null;
		for(Combatant combatant : this.combatants) {
			combatant.leaveArena();
		}
	}
}