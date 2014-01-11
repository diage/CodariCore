package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.settings;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class SelectTimeIcon extends MenuIcon {

	public SelectTimeIcon(Combatant combatant, Menu menu) {
		super(Material.BRICK_STAIRS, combatant, menu, "Select Win Condition Time");
	}

}
