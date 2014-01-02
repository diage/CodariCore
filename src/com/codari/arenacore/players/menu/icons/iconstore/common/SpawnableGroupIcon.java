package com.codari.arenacore.players.menu.icons.iconstore.common;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class SpawnableGroupIcon extends MenuIcon {
	
	public SpawnableGroupIcon(Combatant combatant, Menu menu, String displayName) {
		super(Material.ARROW, combatant, menu, displayName);
	}
}
