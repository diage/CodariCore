package com.codari.arenacore.players.menu.icons.iconstore.utilitymenu;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class HelpIcon extends MenuIcon {

	public HelpIcon(Combatant combatant, Menu menu) {
		super(Material.COOKED_CHICKEN, combatant, menu, "Help");
	}

}
