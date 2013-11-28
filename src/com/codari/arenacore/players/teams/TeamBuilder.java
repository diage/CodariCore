package com.codari.arenacore.players.teams;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.codari.api5.CodariI;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;


public class TeamBuilder implements Listener {
	public static Team createNewTeam(Player player, String teamName) {
		Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
		Team team = new TeamCore(teamName, combatant);
		team.addToTeam(combatant);
		team.setLeader(combatant); 
		return team;
	}
	
	public static void addPlayer(TeamCore team, Player player) {
		Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
		team.addToTeam(combatant);
	}
	
	public static void removePlayer(TeamCore team, Player player) {
		Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
		if(combatant.checkIfLeader() && team.getTeamSize() > 1) {
			Combatant teamMate = team.getTeamMates(combatant).get(0);
			teamMate.setLeader(true);
			teamMate.getPlayerReference().getPlayer().sendMessage("You are now the leader of " + "\"" + team.getTeamName() + "\"");
		}
		team.removeFromTeam(combatant);		
	}
}
