package com.codari.arenacore.develop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.arenacore.ArenaBuilderCore;
import com.codari.arenacore.ArenaCore;
import com.codari.arenacore.rules.GameRuleCore;

public class NewArenaCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("new2v2arena") && args.length == 1) {
			Player player = (Player) sender;
			String arenaName = args[0];
			
			//DA CHAIN OF COMMAND
			GameRuleCore gameRule = new GameRuleCore();
			gameRule.setMatchDurationInfinite();
			gameRule.setTeamSize(2);
			//gameRule.addWinCondition(winConditionCore);
			ArenaBuilderCore builder = new ArenaBuilderCore(gameRule);
			new ArenaCore(arenaName, builder);
			player.sendMessage("You have created a new 2v2 arena named " + args[0]);
			return true;
		}
		return false;
	} 
}
