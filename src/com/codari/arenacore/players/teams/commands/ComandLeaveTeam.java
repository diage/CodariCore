package com.codari.arenacore.players.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class ComandLeaveTeam implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && command.getName().equalsIgnoreCase("leaveteam") && args.length == 0) {
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
}
