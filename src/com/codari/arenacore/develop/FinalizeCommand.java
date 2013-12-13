package com.codari.arenacore.develop;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.apicore.command.CodariCommand;
import com.codari.arena5.ArenaBuilder;
import com.codari.arenacore.ArenaManagerCore;

public class FinalizeCommand implements CodariCommand {
	public static final String COMMAND_NAME = "f";
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[1].equalsIgnoreCase(COMMAND_NAME) && args.length == 2) {
			ArenaBuilder arenaBuilder = ((ArenaManagerCore)Codari.getArenaManager()).getArenaBuilder(args[1]);

			Codari.getArenaManager().buildArena(args[1], arenaBuilder);

			Bukkit.broadcastMessage("Finalized!");
			return true;
		}
		return false;
	}


	@Override
	public String usage() {
		return "Finalizes the creation of the provided arena. #arenaname";
	}
}
