package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.rolesettings;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class RoleOptionsIcon extends MenuIcon {

	public RoleOptionsIcon(Combatant combatant, Menu menu, String roleName) {
		super(Material.SANDSTONE_STAIRS, combatant, menu, roleName);
	}

}
