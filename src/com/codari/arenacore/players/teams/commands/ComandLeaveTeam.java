package com.codari.arenacore.players.teams.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.apicore.command.CodariCommand;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class ComandLeaveTeam implements CodariCommand {
	public static final String COMMAND_NAME = "l";
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[0].equalsIgnoreCase(COMMAND_NAME) && args.length == 1) {
			Player player = (Player) sender;
			Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
			TeamCore team = (TeamCore) combatant.getTeam();			

			if(team != null) {	
				TeamBuilder.removePlayer(team, player);
				player.sendMessage("You have left " + "\"" + team.getTeamName() + "\"." );
				for(Player teamate : team.getPlayers()) {
					teamate.sendMessage("\"" + player.getName() + "\" has left your team.");
				}
				return true;
			} else {
				player.sendMessage("You have not left any team because you were not part of any team.");
				return true;
			}
		}
		return false;
	}

	@Override
	public String usage() {
		return "Type in \"ca " + COMMAND_NAME;
	}
}
