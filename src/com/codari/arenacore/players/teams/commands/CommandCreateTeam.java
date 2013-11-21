package com.codari.arenacore.players.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arenacore.players.teams.TeamBuilder;

public class CommandCreateTeam implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("createteam") && args.length == 1) {
			Player player = (Player) sender;
			if(Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(player)).combatants().size() == 0) {			
				TeamBuilder.createNewTeam(player, args[0]);
				return true;
			} else {
				player.sendMessage("You are already on a team. You have to leave your team before you start a new one.");
				return true;
			}
		}
		return false;
	} 
}
