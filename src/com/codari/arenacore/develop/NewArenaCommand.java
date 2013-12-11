package com.codari.arenacore.develop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.api5.util.Time;
import com.codari.arena.rules.WinCondition2v2;
import com.codari.arena5.ArenaBuilder;
import com.codari.arenacore.ArenaManagerCore;
import com.codari.arenacore.rules.GameRuleCore;

public class NewArenaCommand implements CommandExecutor {
	private static int teamSize = 2;
	private static int numberOfPointsToWin = 100;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("new2v2arena") && args.length == 1) {
			Player player = (Player) sender;
			String arenaName = args[0];

			if(!((ArenaManagerCore)Codari.getArenaManager()).containsArenaBuild(arenaName)) {
				//Gamerule construction//
				GameRuleCore gameRule = new GameRuleCore();
				gameRule.setMatchDurationInfinite();
				gameRule.setTeamSize(teamSize);		//set team size to 2
				gameRule.addWinCondition(new WinCondition2v2(numberOfPointsToWin));
				//ArenaBuilder construction//
				ArenaBuilder arenaBuilder = ((ArenaManagerCore) Codari.getArenaManager()).getArenaBuider(gameRule);
				((ArenaManagerCore) Codari.getArenaManager()).addArenaBuilder(arenaName, arenaBuilder);
				arenaBuilder.createRandomTimelineGroup("trap", new Time(0, 30), new Time(0, 30));
				arenaBuilder.createRandomTimelineGroup("objective", new Time(0, 30), new Time(0, 30));
				arenaBuilder.createRandomTimelineGroup("spawner", new Time(0, 30), new Time(0, 30));
				player.sendMessage("You have created a new 2v2 arena builder.");
				return true;
			} else {
				player.sendMessage("There is already an arena with the name " + args[0]);
				return true;
			}
		}
		return false;
	} 
}
