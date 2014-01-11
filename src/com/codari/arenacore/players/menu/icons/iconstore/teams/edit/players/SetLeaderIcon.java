package com.codari.arenacore.players.menu.icons.iconstore.teams.edit.players;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SetLeaderIcon extends ExecutableIcon {
	private Combatant teamMate;
	
	public SetLeaderIcon(Combatant combatant, Combatant teamMate) {
		super(Material.DRAGON_EGG, combatant, "Set as Team Leader");
		this.teamMate = teamMate;
	}

	@Override
	public void click() {
		if(this.getCombatant().checkIfLeader()) {
			this.getCombatant().setLeader(false);
			this.teamMate.setLeader(true);
			this.teamMate.getPlayer().sendMessage(ChatColor.GREEN + "You are now the leader of your team.");
		}
	}

}
