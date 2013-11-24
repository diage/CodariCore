package com.codari.arenacore.players.teams;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;


public class TeamBuilder implements Listener {
	public static Team createNewTeam(Player player, String teamName) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(player);
		Team team = new TeamCore(teamName, combatant);
		team.addToTeam(combatant);
		team.setLeader(combatant); 
		return team;
	}
	
	public static void invitePlayer(TeamCore team, Player player) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(player);
		team.addToTeam(combatant);
	}
	
	public static void removePlayer(TeamCore team, Player player) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(player);
		team.removeFromTeam(combatant);		
	}
}
