package com.codari.arenacore.players.menu.icons.iconstore.menu;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.menustore.function.KitBuildersFMenu;

public class KitBuildersIcon extends MenuIcon {

	public KitBuildersIcon(Combatant combatant) {
		super(Material.RECORD_9, combatant, new KitBuildersFMenu(), "KitBuilders");
		// TODO Auto-generated constructor stub
	}

}
