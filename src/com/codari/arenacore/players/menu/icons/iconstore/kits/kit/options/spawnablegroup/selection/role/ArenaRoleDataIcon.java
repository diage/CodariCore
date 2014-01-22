package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.role;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaRoleDataIcon extends MenuIcon {

	public ArenaRoleDataIcon(Combatant combatant, Menu menu) {
		super(Material.SUGAR_CANE_BLOCK, combatant, menu, "Set Number of Roles");
	}

}
