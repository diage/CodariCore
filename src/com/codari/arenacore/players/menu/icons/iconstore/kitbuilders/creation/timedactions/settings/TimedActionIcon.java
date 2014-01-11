package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.timedactions.settings;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class TimedActionIcon extends MenuIcon {

	public TimedActionIcon(Combatant combatant, Menu menu, String timedActionName) {
		super(Material.COAL, combatant, menu, timedActionName);
	}
	
}
