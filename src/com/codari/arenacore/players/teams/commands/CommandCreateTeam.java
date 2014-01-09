package com.codari.arenacore.players.teams.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.apicore.command.CodariCommand;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.teams.TeamBuilder;

public class CommandCreateTeam implements CodariCommand {
	public static final String COMMAND_NAME = "c";

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[0].equalsIgnoreCase(COMMAND_NAME) && args.length == 2) {
			Player player = (Player) sender;
			Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant(player);
			if(combatant.getTeam() == null) {
				if(!Codari.getTeamManager().containsTeam(args[1])) {
					TeamBuilder.createNewTeam(player, args[1]);
					player.sendMessage("You have created a new team named " + "\"" + args[1] + "\"");
					return true;
				} else {
					player.sendMessage("A team with that name already exists!");
					return false;
				}
			} else {
				player.sendMessage("You are already on the team: " + "\"" + combatant.getTeam().getTeamName() + "\"" + ". "
						+ "You have to leave your team before you start a new one.");
				return true;
			}
		}
		return false;
	} 


	@Override
	public String usage() {
		return "Create a team with the specified team name. #teamname";
	}
}
