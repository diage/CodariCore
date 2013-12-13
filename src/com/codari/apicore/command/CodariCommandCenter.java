package com.codari.apicore.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.apicore.CodariCore;

public class CodariCommandCenter implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equals("ca")) {
			if(args.length > 0) {
				return ((CodariCore)CodariI.INSTANCE).getCommandRegister().executeCommand(sender, args);
			} 
			
			for(String commands : ((CodariCore)CodariI.INSTANCE).getCommandRegister().getCommands()) {
				((Player)sender).sendMessage(ChatColor.AQUA + commands + 
						ChatColor.DARK_AQUA + ": " + ((CodariCore)CodariI.INSTANCE).getCommandRegister().getCommand(commands).usage());
			}
		}
		return false;
	}
}
