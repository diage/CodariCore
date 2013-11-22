package com.codari.arenacore.develop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.Arena;
import com.codari.arenacore.ArenaManagerCore;

public class ArenaDevelopmentCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("arenakit") && args.length == 1) {
			Player player = (Player) sender;
			ArenaManagerCore arenaManagerCore = (ArenaManagerCore) Codari.INSTANCE.getArenaManager();
			Arena arena = arenaManagerCore.getArena(args[0]);
			if(arena == null) {
				player.sendMessage("You entered the name of an arena that doesn't exist.");
				return false;
			}
			ArenaDevelopmentKit arenaDevelopmentKit = new ArenaDevelopmentKit(arena);
			arenaDevelopmentKit.createArenaDevelopmentObjects(player);
			return true;
		}
		return false;
	} 	
}
