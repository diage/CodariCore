package com.codari.arenacore.players.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.apicore.command.CodariCommand;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class CommandInvitePlayerToTeam implements CodariCommand {
	public static final String COMMAND_NAME = "i";
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[0].equalsIgnoreCase(COMMAND_NAME) && args.length == 2) {
			Player player = (Player) sender;
			Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
			TeamCore team = (TeamCore) combatant.getTeam();			

			Player invitedPlayer = Bukkit.getPlayer(args[1]);

			//Checks if it's not a valid player
			if(invitedPlayer == null) {
				player.sendMessage(ChatColor.RED + "Could not find the player named " + ChatColor.BOLD + "\""  + args[1] + "\".");
				return true;
			}

			Combatant invitedCombatant = CodariI.INSTANCE.getArenaManager().getCombatant(invitedPlayer);
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

	@Override
	public String usage() {
		return "Type in \"ca " + COMMAND_NAME + " (playername)\"";
	}	
}
