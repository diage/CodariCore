package com.codari.arenacore.players.teams.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.teams.TeamBuilder;

public class CommandInvitePlayerToTeam implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("invite") && args.length == 1) {
			Player player = (Player) sender;
			Team team = Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(player));			
				
			Player invitedPlayer = Bukkit.getPlayer(args[0]);
			TeamBuilder.invitePlayer(team, invitedPlayer);
			return true;

		}
		return false;
	} 
}
