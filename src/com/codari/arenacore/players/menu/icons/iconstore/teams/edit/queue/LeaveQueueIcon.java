package com.codari.arenacore.players.menu.icons.iconstore.teams.edit.queue;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.teams.TeamCore;

public class LeaveQueueIcon extends ExecutableIcon {
	private Team team;
	
	public LeaveQueueIcon(Combatant combatant, Team team) {
		super(Material.STAINED_CLAY, combatant, "Leave Queue");
		this.team = team;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(((TeamCore)this.team).checkIfInQueue()) {
			if(((ArenaManagerCore) Codari.getArenaManager()).removeFromQueue(this.team)) {
				for(Player teamPlayer : this.team.getPlayers()) {
					teamPlayer.sendMessage(ChatColor.GREEN + "Your team, " +  this.team.getTeamName() + ", was successfully removed from the queue!");
				}
			}
		} else {
			player.sendMessage(ChatColor.RED + "Your team can't leave a queue if your team isn't part of one!");
		}
	}

}
