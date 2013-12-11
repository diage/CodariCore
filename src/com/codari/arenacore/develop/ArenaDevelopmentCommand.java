package com.codari.arenacore.develop;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arenacore.players.combatants.CombatantCore;

public class ArenaDevelopmentCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("arenakit") && args.length == 1) {
			Player player = (Player) sender;
			((CombatantCore) Codari.getArenaManager().getCombatant(player)).startBuilding(args[0]);
			ArenaDevelopmentKit.createArenaDevelopmentObjects(player);
			Bukkit.broadcastMessage("TEST ONE - PASSES GET COMBATANT");
			return true;
		}
		return false;
	} 	
}
