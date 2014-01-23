package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.selection;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class NewKitBuilderIcon extends MenuIcon {

	public NewKitBuilderIcon(Combatant combatant, Menu menu) {
		super(Material.COAL_ORE, combatant, menu, "Create New Kit Builder");
	}
	
}
