package com.codari.arenacore.players.teams.queue;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//FIXME - Ability to leave queue
public class LeaveQueueCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("leavequeue") && args.length == 0) {
			//Player player = (Player) sender;
		}
		return false;
	}
}
