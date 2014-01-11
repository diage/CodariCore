package com.codari.arenacore.players.menu.icons.iconstore.teams.edit.queue;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class JoinQueueIcon extends MenuIcon {

	public JoinQueueIcon(Combatant combatant, Menu menu) {
		super(Material.SUGAR_CANE, combatant, menu, "Join Queue");
	}

}
