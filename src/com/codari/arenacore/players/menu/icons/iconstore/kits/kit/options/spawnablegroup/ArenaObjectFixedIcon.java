package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaObjectFixedIcon extends MenuIcon {

	public ArenaObjectFixedIcon(Combatant combatant, Menu menu, String arenaObjectName) {
		super(Material.POISONOUS_POTATO, combatant, menu, arenaObjectName);
	}
}
