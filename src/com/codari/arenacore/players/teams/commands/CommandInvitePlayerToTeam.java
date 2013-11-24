package com.codari.arenacore.players.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class CommandInvitePlayerToTeam implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("invite") && args.length == 1) {
			System.out.println("Going into invite player command");
			Player player = (Player) sender;
			Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(player);
			TeamCore team = (TeamCore) combatant.getTeam();			
			
			Player invitedPlayer = Bukkit.getPlayer(args[0]);
			Combatant invitedCombatant = Codari.INSTANCE.getArenaManager().getCombatant(invitedPlayer);
			TeamCore invitedPlayerTeam = (TeamCore) invitedCombatant.getTeam();
			
			//Checks if player is already on a team
			if(invitedPlayerTeam.getTeamName() != null) {
				System.out.println("Invited Player is already on a team.");
				player.sendMessage(invitedPlayer.getName() + " is already on a team.");
				return true;
			} else {
				invitedPlayer.sendMessage(player.getName() + " has invited you to join " + team.getTeamName() + ". Would you like to join?");
				//Method so that player could accept or decline invite here
				System.out.println("Player has been invited to your team.");
				TeamBuilder.invitePlayer(team, invitedPlayer);
				return true;				
			}
		}
		return false;
	} 
}
