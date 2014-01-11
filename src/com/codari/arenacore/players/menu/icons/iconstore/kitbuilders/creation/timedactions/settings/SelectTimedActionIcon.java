package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.timedactions.settings;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class SelectTimedActionIcon extends MenuIcon{

	public SelectTimedActionIcon(Combatant combatant, Menu menu) {
		super(Material.COBBLESTONE_STAIRS, combatant, menu, "Add Timed Actions");
	}

}
