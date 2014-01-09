package com.codari.arenacore.players.menu.icons.iconstore.teams.edit;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class QueueIcon extends MenuIcon {

	public QueueIcon(Combatant combatant, Menu menu) {
		super(Material.IRON_HOE, combatant, menu, "Queue Options");
	}

}
