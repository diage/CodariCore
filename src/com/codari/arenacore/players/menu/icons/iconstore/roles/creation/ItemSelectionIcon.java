package com.codari.arenacore.players.menu.icons.iconstore.roles.creation;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ItemSelectionIcon extends MenuIcon {

	public ItemSelectionIcon(Combatant combatant, Menu menu) {
		super(Material.THIN_GLASS, combatant, menu, "Select Items");
	}

}
