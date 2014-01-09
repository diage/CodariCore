package com.codari.arenacore.players.menu.icons.iconstore.teams.edit.players;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class KickPlayerIcon extends ExecutableIcon {
	private Combatant teamMate;
	
	public KickPlayerIcon(Combatant combatant, Combatant teamMate) {
		super(Material.BLAZE_POWDER, combatant, "Kick Player");
		this.teamMate = teamMate;
	}

	@Override
	public void click() {
		if(this.getCombatant().getTeam() != null && this.getCombatant().getTeam().combatants().contains(this.teamMate)) {
			TeamBuilder.removePlayer((TeamCore) this.getCombatant().getTeam(), this.teamMate.getPlayer());
			this.teamMate.getPlayer().sendMessage(ChatColor.RED + "You have been removed from your team.");
			//FIXME - remove the player icon from the previous PlayerOptions page
		}
	}

}
