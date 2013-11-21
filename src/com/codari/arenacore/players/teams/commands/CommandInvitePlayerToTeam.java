package com.codari.arenacore.players.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class CommandInvitePlayerToTeam implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("invite") && args.length == 1) {
			Player player = (Player) sender;
			TeamCore team = (TeamCore) Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(player));			
				
			Player invitedPlayer = Bukkit.getPlayer(args[0]);
			
			invitedPlayer.sendMessage(player.getName() + " has invited you to join " + team.getTeamName() + ". Would you like to join?");
			//Method so that player could accept or decline invite here
			TeamBuilder.invitePlayer(team, invitedPlayer);
			return true;

		}
		return false;
	} 
}
