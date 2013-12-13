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
		return "Provides a player with the necessary items to construct an arena for the given arena. #arenaname";
	}
}
