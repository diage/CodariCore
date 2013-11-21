package com.codari.arenacore.players.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class ComandLeaveTeam implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("invite") && args.length == 0) {
			Player player = (Player) sender;
			TeamCore team = (TeamCore) Codari.INSTANCE.getArenaManager().getTeam(Codari.INSTANCE.getArenaManager().getCombatant(player));			
			
			if(team != null) {	
				TeamBuilder.removePlayer(team, player);
				return true;
			}

		}
		return false;
	} 
}
