package com.codari.apicore.command;

import org.bukkit.command.CommandSender;

public interface CodariCommand {
	public boolean execute(CommandSender sender, String[] args);
	
	public String usage();
}
