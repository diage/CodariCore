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
		team.setLeader(); //Is this correct?
		return team;
	}
	
	public static void invitePlayer(Team team, Player player) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(player);
		team.combatants().add(combatant);
	}
	
	public static void removePlayer(Team team, Player player) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(player);
		int combatantTeamSpot = 0;
		for(int i = 0; i < team.combatants().size(); i++) {
			Combatant possibleCombatant = team.combatants().get(i);
			if(possibleCombatant.equals(combatant)) {
				combatantTeamSpot = i;
				break;
			}
		}
		team.combatants().remove(combatantTeamSpot);
	}
}
