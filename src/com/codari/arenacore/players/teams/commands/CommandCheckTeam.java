package com.codari.arenacore.players.teams.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.apicore.command.CodariCommand;
import com.codari.arena5.players.combatants.Combatant;

public class CommandCheckTeam implements CodariCommand {
	public static final String COMMAND_NAME = "t";

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[0].equalsIgnoreCase(COMMAND_NAME) && args.length == 1) {
			Player player = (Player) sender;
			Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
			if(combatant.getTeam() == null) {			
				player.sendMessage(ChatColor.RED + "You are not on a team!");
				return true;
			} else {
				String message = "";
				for(int i = 0; i < combatant.getTeam().getPlayers().size(); i++) {
					message += combatant.getTeam().getPlayers().get(i).getName() + " ";
				}
				player.sendMessage(ChatColor.GREEN + message);
				return true;
			}
		}
		return false;
	} 


	@Override
	public String usage() {
		return "See who's currently on your team.";
	}
}
