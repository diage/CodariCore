package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.role;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaRoleDataIcon extends MenuIcon {

	public ArenaRoleDataIcon(Combatant combatant, Menu menu, String roleName) {
		super(Material.BLAZE_ROD, combatant, menu, "Set Number of " + roleName + " Roles");
	}

}
