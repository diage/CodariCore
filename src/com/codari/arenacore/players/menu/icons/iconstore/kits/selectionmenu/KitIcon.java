package com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class KitIcon extends MenuIcon {	
	public KitIcon(Combatant combatant, Menu menu, String kitName) {
		super(Material.OBSIDIAN, combatant, menu, kitName);
	}
}