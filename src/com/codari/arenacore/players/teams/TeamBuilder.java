package com.codari.arenacore.players.teams;

import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;


public class TeamBuilder {
	public static Team createNewTeam(Player player, String teamName) {
		Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
		Team team = new TeamCore(teamName, combatant);
		team.setLeader(combatant); 
		Codari.getTeamManager().putTeam(teamName, team);
		return team;
	}
	
	public static void addPlayer(TeamCore team, Player player) {
		Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
		team.addToTeam(combatant);
	}
	
	public static void removePlayer(TeamCore team, Player player) {
		Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
		if(team.getTeamSize() > 1) {
			if(combatant.checkIfLeader()) {
				Combatant teamMate = team.getTeamMates(combatant).get(0);
				teamMate.setLeader(true);
				teamMate.getPlayer().sendMessage("You are now the leader of " + "\"" + team.getTeamName() + "\"");
			}
		} else {
			removeTeamFromTeamManager(team.getTeamName());
		}
		team.removeFromTeam(combatant);		
	}
	
	private static void removeTeamFromTeamManager(String teamName) {
		Codari.getTeamManager().removeTeam(teamName);
	}
}
