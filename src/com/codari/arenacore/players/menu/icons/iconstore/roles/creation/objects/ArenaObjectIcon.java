package com.codari.arenacore.players.menu.icons.iconstore.roles.creation.objects;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaObjectIcon extends MenuIcon {

	public ArenaObjectIcon(Combatant combatant, Menu menu, String arenaObjectName) {
		super(Material.SULPHUR, combatant, menu, arenaObjectName);
	}

}
