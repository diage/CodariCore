package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaObjectPersistentIcon extends MenuIcon {

	public ArenaObjectPersistentIcon(Combatant combatant, Menu menu, String arenaObjectName) {
		super(Material.BIRCH_WOOD_STAIRS, combatant, menu, arenaObjectName);
	}
}
