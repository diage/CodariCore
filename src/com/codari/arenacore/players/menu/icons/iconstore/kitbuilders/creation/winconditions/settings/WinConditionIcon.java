package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.settings;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class WinConditionIcon extends MenuIcon {

	public WinConditionIcon(Combatant combatant,Menu menu, String winConditionName) {
		super(Material.BLAZE_POWDER, combatant, menu, winConditionName);
	}

}
