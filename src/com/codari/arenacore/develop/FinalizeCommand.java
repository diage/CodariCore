package com.codari.arenacore.develop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.ArenaBuilder;
import com.codari.arenacore.ArenaManagerCore;

public class FinalizeCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("finalize") && args.length == 1) {
			//Player player = (Player) sender;
			ArenaBuilder arenaBuilder = ((ArenaManagerCore)Codari.getArenaManager()).getArenaBuilder(args[0]);
			
			Codari.getArenaManager().buildArena(args[0], arenaBuilder);
			
			Bukkit.broadcastMessage("Finalized!");
			return true;
		}
		return false;
	}

}
