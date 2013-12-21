package com.codari.arenacore.develop;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.api5.util.Time;
import com.codari.apicore.command.CodariCommand;
import com.codari.arena.rules.ArenaRoleDeclaration;
import com.codari.arena.rules.WinCondition2v2;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.arena.rules.GameRuleCore;

public class NewArenaCommand implements CodariCommand {
	public static final String COMMAND_NAME = "n";
	
	private static int teamSize = 2;
	private static int numberOfPointsToWin = 100;
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[0].equalsIgnoreCase(COMMAND_NAME) && args.length == 2) {
			Player player = (Player) sender;
			String arenaName = args[1];

			if(!((ArenaManagerCore)Codari.getArenaManager()).containsArenaBuild(arenaName)) {
				//Gamerule construction//
				GameRuleCore gameRule = new GameRuleCore();
				gameRule.setMatchDurationInfinite();
				gameRule.setTeamSize(teamSize);		//set team size to 2
				gameRule.addWinCondition(new WinCondition2v2(numberOfPointsToWin));
				gameRule.addRoleDeclaration(new ArenaRoleDeclaration());
				//ArenaBuilder construction//
				ArenaBuilder arenaBuilder = ((ArenaManagerCore) Codari.getArenaManager()).getArenaBuider(gameRule);
				((ArenaManagerCore) Codari.getArenaManager()).addArenaBuilder(arenaName, arenaBuilder);
				arenaBuilder.createRandomTimelineGroup("trap", new Time(0, 30), new Time(0, 30));
				arenaBuilder.createRandomTimelineGroup("objective", new Time(0, 30), new Time(0, 30));
				arenaBuilder.createRandomTimelineGroup("spawner", new Time(0, 30), new Time(0, 30));
				player.sendMessage("You have created a new 2v2 arena builder.");
				return true;
			} else {
				player.sendMessage("There is already an arena with the name " + args[1]);
				return true;
			}
		}
		return false;
	}
	@Override
	public String usage() {
		return "Create a new 2v2 arena. #arenaname";
	}
}
