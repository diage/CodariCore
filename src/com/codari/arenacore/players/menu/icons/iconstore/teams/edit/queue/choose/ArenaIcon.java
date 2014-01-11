package com.codari.arenacore.players.menu.icons.iconstore.teams.edit.queue.choose;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.teams.TeamCore;

public class ArenaIcon extends ExecutableIcon {
	private String arenaName;
	private Team team;
	
	public ArenaIcon(Combatant combatant, String arenaName, Team team) {
		super(Material.FENCE_GATE, combatant, arenaName);
		this.arenaName = arenaName;
		this.team = team;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(!((TeamCore) this.team).checkIfInQueue()) {
			if(((ArenaManagerCore) Codari.getArenaManager()).addToQueue(this.arenaName, this.team)) {
				for(Player teamPlayer : this.team.getPlayers()) {
					teamPlayer.sendMessage(ChatColor.GREEN + "Your team, " +  team.getTeamName() + ", was successfully added to the queue!");
				}			
			}
		} else {
			player.sendMessage(ChatColor.RED + "Your team can't join another queue if your team is already part of one!");
		}
	}

}
