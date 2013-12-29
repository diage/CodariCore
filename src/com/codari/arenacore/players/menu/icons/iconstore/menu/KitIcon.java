package com.codari.arenacore.players.menu.icons.iconstore.menu;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.menustore.function.KitFMenu;

public class KitIcon extends MenuIcon {

	public KitIcon(Combatant combatant, String kitName) {
		super(Material.OBSIDIAN, combatant, new KitFMenu(), kitName);
		// TODO Auto-generated constructor stub
	}

}
