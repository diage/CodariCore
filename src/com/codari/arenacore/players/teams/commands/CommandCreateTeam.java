package com.codari.arenacore.players.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.teams.TeamBuilder;

public class CommandCreateTeam implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("createteam") && args.length == 1) {
			Player player = (Player) sender;
			Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
			if(combatant.getTeam() == null) {			
				TeamBuilder.createNewTeam(player, args[0]);
				player.sendMessage("You have created a new team named " + "\"" + args[0] + "\"");
				return true;
			} else {
				player.sendMessage("You are already on the team: " + "\"" + combatant.getTeam().getTeamName() + "\"" + ". "
						+ "You have to leave your team before you start a new one.");
				return true;
			}
		}
		return false;
	} 
}
