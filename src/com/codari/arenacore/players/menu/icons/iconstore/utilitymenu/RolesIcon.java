package com.codari.arenacore.players.menu.icons.iconstore.utilitymenu;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

/* 
 * Icon to display all the roles
 * */
public class RolesIcon extends MenuIcon {

	public RolesIcon(Combatant combatant, Menu menu) {
		super(Material.OBSIDIAN, combatant, menu, "Roles");
	}

}
