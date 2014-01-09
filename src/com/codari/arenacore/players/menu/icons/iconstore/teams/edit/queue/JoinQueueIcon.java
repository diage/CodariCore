package com.codari.arenacore.players.menu.icons.iconstore.teams.edit.queue;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.SelectQueueIconListener;

public class JoinQueueIcon extends ExecutableIcon {
	private Team team;
	
	public JoinQueueIcon(Combatant combatant, Team team) {
		super(Material.SUGAR_CANE, combatant, "Join Queue");
		this.team = team;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(SelectQueueIconListener.requeustedQueueNames.containsKey(player.getName())) {
			String arenaName = SelectQueueIconListener.requeustedQueueNames.get(player.getName());
			if(((ArenaManagerCore)Codari.getArenaManager()).containsArena(arenaName)) {
				if(Codari.getArenaManager().addToQueue(arenaName, this.team)) {
					for(Player teamPlayer : this.team.getPlayers()) {
						teamPlayer.sendMessage(ChatColor.GREEN + "Your team, " +  this.team.getTeamName() + ", was successfully added to the queue!");
					}
				}
			} else {
				player.sendMessage(ChatColor.RED + arenaName + " is not a valid Arena name!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for an Arena first!");
		}
	}
}
