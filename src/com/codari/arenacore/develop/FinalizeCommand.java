package com.codari.arenacore.develop;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.apicore.command.CodariCommand;
import com.codari.arena5.Arena;
import com.codari.arena5.ArenaBuilder;
import com.codari.arenacore.ArenaCore;
import com.codari.arenacore.ArenaManagerCore;

public class FinalizeCommand implements CodariCommand {
	public final static String COMMAND_NAME = "f";
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[0].equalsIgnoreCase(COMMAND_NAME) && args.length == 2) {
			ArenaBuilder arenaBuilder = ((ArenaManagerCore)Codari.getArenaManager()).getArenaBuilder(args[1]);

			Arena arena = Codari.getArenaManager().buildArena(args[1], arenaBuilder);
			File file = new File(CodariI.INSTANCE.getDataFolder(), args[1] + ".dat");
			CodariI.INSTANCE.getDataFolder().mkdirs();
			((ArenaCore) arena).serializeTest(file);

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
