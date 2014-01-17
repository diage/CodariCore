package com.codari.arenacore.players.menu.icons.iconstore.teams.options;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class LeaveTeamIcon extends ExecutableIcon {
	
	public LeaveTeamIcon(Combatant combatant) {
		super(Material.REDSTONE_BLOCK, combatant, "Leave Team");
	}

	@Override
	public void click() {
		if((TeamCore) this.getCombatant().getTeam().getArena() == null) {
			if(!((TeamCore) this.getCombatant().getTeam()).checkIfInQueue()) {
				if(this.getCombatant().getTeam() != null) {
					((CombatantCore) this.getCombatant()).getDynamicMenuManager().addNoTeamIcons();
					TeamBuilder.removePlayer((TeamCore) this.getCombatant().getTeam(), this.getCombatant().getPlayer());
					this.getCombatant().getPlayer().sendMessage(ChatColor.BLUE + "You have left your team.");
				}
			} else {
				this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "You can't leave your team while you're in a queue!");
			}
		} else {
			this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "You can't leave your team while you're in an arena!");
		}
	}
}
