package com.codari.arenacore.players.menu.icons.iconstore.roles.selection;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class RoleIcon extends MenuIcon {

	public RoleIcon( Combatant combatant, Menu menu, String roleName) {
		super(Material.RED_MUSHROOM, combatant, menu, roleName);
	}

}
