package com.codari.arenacore.develop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.arena.rules.WinCondition2v2;
import com.codari.arenacore.ArenaBuilderCore;
import com.codari.arenacore.ArenaManagerCore;
import com.codari.arenacore.rules.GameRuleCore;

public class NewArenaCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("new2v2arena") && args.length == 0) {
			Player player = (Player) sender;
			
			//Setting up the 2v2 Arena
			int teamSize = 2;
			int numberOfPointsToWin = 100;
			
			//Order
			GameRuleCore gameRule = new GameRuleCore();
			gameRule.setMatchDurationInfinite();
			gameRule.setTeamSize(teamSize);
			gameRule.addWinCondition(new WinCondition2v2(numberOfPointsToWin));
			ArenaBuilderCore arenaBuilder = new ArenaBuilderCore(gameRule);
			ArenaManagerCore arenaManager = (ArenaManagerCore) CodariI.INSTANCE.getArenaManager();
			arenaManager.addArenaBuilder(player.getName(), arenaBuilder);
			
			player.sendMessage("You have created a new 2v2 arena builder.");
			return true;
		}
		return false;
	} 
}
