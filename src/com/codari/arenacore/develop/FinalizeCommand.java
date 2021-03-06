package com.codari.arenacore.develop;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.apicore.command.CodariCommand;
import com.codari.arenacore.arena.ArenaBuilder;
import com.codari.arenacore.arena.ArenaManagerCore;

public class FinalizeCommand implements CodariCommand {
	public final static String COMMAND_NAME = "f";
	public final static String LOAD_NAME = "fl";
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[0].equalsIgnoreCase(COMMAND_NAME) && args.length == 2) {
			ArenaBuilder arenaBuilder = ((ArenaManagerCore)Codari.getArenaManager()).getArenaBuilder(args[1]);

			((ArenaManagerCore)Codari.getArenaManager()).buildArena(args[1], arenaBuilder);
			Bukkit.broadcastMessage("Finalized!");	//TODO
			return true;
		} else if(sender instanceof Player && args[0].equalsIgnoreCase(LOAD_NAME) && args.length == 2) {
			//File file = new File(CodariI.INSTANCE.getDataFolder(), args[1] + ".dat");
			//((ArenaManagerCore)Codari.getArenaManager()).loadArena(file);
			//sender.sendMessage("" + ChatColor.UNDERLINE + ChatColor.BOLD +
					//ChatColor.LIGHT_PURPLE + "LOADED ARENA NAMED FROM " + file);
			return true;
		}
		return false;
	}


	@Override
	public String usage() {
		return "Finalizes the creation of the provided arena. #arenaname";
	}
}
