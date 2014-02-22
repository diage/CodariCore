package com.codari.arenacore.players.teams.queue;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.apicore.command.CodariCommand;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.ArenaManagerCore;

public class JoinQueueCommand implements CodariCommand {
	public static final String COMMAND_NAME = "j";
	
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if(sender instanceof Player && args[0].equalsIgnoreCase(COMMAND_NAME) && args.length == 2) {
			Player player = (Player) sender;
			Team team = Codari.getArenaManager().getCombatant(player).getTeam();
			Arena arena = Codari.getArenaManager().getArena(args[1]);

			if(checkIfArenaIsValid(arena, player) && checkIfPlayerHasTeam(team, player)) {  				
				if(((ArenaManagerCore) Codari.getArenaManager()).addToQueue(args[1], team)) {
					player.sendMessage(ChatColor.GREEN + "Your team, " +  team.getTeamName() + ", was successfully added to the queue!");
					return true;
				}			
			}
		}
		return false;
	}	
	
	@Override
	public String usage() {
		return "Join the queue of the provided arena. #arenaname";
	}

	private static boolean checkIfArenaIsValid(Arena arena, Player player) {
		if(arena == null) {
			player.sendMessage(ChatColor.RED + "That arena does not exist!");
		}
		return true;
	}

	private static boolean checkIfPlayerHasTeam(Team team, Player player) {
		if(team == null) {
			player.sendMessage(ChatColor.RED + "You can't join the arena if you're not on a team!");
			return false;
		}
		return true;
	}
}
