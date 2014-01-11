package com.codari.arenacore.players.menu.icons.iconstore.teams.options;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.menus.menustore.teams.InitialTeamOptions;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class LeaveTeamIcon extends ExecutableIcon {
	private InitialTeamOptions initialTeamOptions;
	
	public LeaveTeamIcon(Combatant combatant, InitialTeamOptions initialTeamOptions) {
		super(Material.REDSTONE_BLOCK, combatant, "Leave Team");
		this.initialTeamOptions = initialTeamOptions;
	}

	@Override
	public void click() {
		if(this.getCombatant().getTeam() != null) {
			this.initialTeamOptions.setNoTeamIcon(this.getCombatant());
			TeamBuilder.removePlayer((TeamCore) this.getCombatant().getTeam(), this.getCombatant().getPlayer());
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "A player doesn't have a team but is trying to leave one.");	//TODO
		}	
	}
	
}
