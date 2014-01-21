package com.codari.arenacore.players.menu.icons.iconstore.roles.selection;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class NewRoleIcon extends MenuIcon {

	public NewRoleIcon(Combatant combatant, Menu menu) {
		super(Material.TNT, combatant, menu, "Create New Role");
	}

}
