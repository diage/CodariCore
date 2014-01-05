package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.selection;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class KitBuilderIcon extends MenuIcon {
	public KitBuilderIcon(Combatant combatant, Menu menu, String kitBuilderName) {
		super(Material.OBSIDIAN, combatant, menu, kitBuilderName);
	}
}
