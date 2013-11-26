package com.codari.arenacore.players.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
			Player player = (Player) sender;
			Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(player);
			TeamCore team = (TeamCore) combatant.getTeam();			
			
			Player invitedPlayer = Bukkit.getPlayer(args[0]);
			
			//Checks if it's not a valid player
			if(invitedPlayer == null) {
				player.sendMessage(ChatColor.RED + "Could not find the player named " + ChatColor.BOLD + "\""  + args[0] + "\".");
				return true;
			}
			
			Combatant invitedCombatant = Codari.INSTANCE.getArenaManager().getCombatant(invitedPlayer);
			TeamCore invitedPlayerTeam = (TeamCore) invitedCombatant.getTeam();
			
			//Checks if player is already on a team
			if(invitedPlayerTeam != null) {
				player.sendMessage(invitedPlayer.getName() + " is already on a team.");
				return true;
			} else {
				invitedPlayer.sendMessage(player.getName() + " has invited you to join " + "\"" + team.getTeamName() + "\". "
						+ "Would you like to join?");
				//Method so that player could accept or decline invite here
				
				if(true/*invited player accepted the invite*/) {
					TeamBuilder.addPlayer(team, invitedPlayer);
					player.sendMessage(invitedPlayer.getName() + " has joined your team.");
				}
				return true;				
			}
		}
		return false;
	} 
}
