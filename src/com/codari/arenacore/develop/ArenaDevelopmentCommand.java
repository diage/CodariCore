package com.codari.arenacore.develop;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.apicore.command.CodariCommand;
import com.codari.arenacore.players.combatants.CombatantCore;

public class ArenaDevelopmentCommand implements CodariCommand {
	public static final String COMMAND_NAME = "k";
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[0].equalsIgnoreCase(COMMAND_NAME) && args.length == 2) {
			Player player = (Player) sender;
			((CombatantCore) Codari.getArenaManager().getCombatant(player)).startBuilding(args[1]);
			ArenaDevelopmentKit.createArenaDevelopmentObjects(player);
			return true;
		}
		return false;
	} 	


	@Override
	public String usage() {
		return "Type in \"ca " + COMMAND_NAME + " (arenaname)\"";
	}
	//	@Override
	//	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	//		if(sender instanceof Player && command.getName().equalsIgnoreCase("arenakit") && args.length == 1) {
	//			Player player = (Player) sender;
	//			((CombatantCore) Codari.getArenaManager().getCombatant(player)).startBuilding(args[0]);
	//			ArenaDevelopmentKit.createArenaDevelopmentObjects(player);
	//			Bukkit.broadcastMessage("TEST ONE - PASSES GET COMBATANT");
	//			return true;
	//		}
	//		return false;
	//	} 	
}
