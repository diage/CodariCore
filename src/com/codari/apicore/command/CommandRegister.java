package com.codari.apicore.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

public class CommandRegister {
	private Map<String, CodariCommand> commands;
	public CommandRegister() {
		this.commands = new HashMap<String, CodariCommand>();
	}
	
	public void registerCommand(String commandName, CodariCommand command) {
		this.commands.put(commandName, command);
	}
	
	public boolean executeCommand(CommandSender sender, String[] args) {		
		return this.commands.get(args[0]).execute(sender, args);
	} 
	
	public Collection<String> getCommands() {
		return this.commands.keySet();
	}
	
	public CodariCommand getCommand(String commandName) {
		return this.commands.get(commandName);
	}
}
