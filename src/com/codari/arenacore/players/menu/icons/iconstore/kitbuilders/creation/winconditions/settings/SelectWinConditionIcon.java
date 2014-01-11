package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.settings;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class SelectWinConditionIcon extends MenuIcon {

	public SelectWinConditionIcon(Combatant combatant, Menu menu) {
		super(Material.STATIONARY_LAVA, combatant, menu, "Add Win Conditions");
	}

}
