package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.rolesettings;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class AddRoleIcon extends MenuIcon {

	public AddRoleIcon(Combatant combatant, Menu menu) {
		super(Material.STONE_PICKAXE, combatant, menu, "Add Role");
	}

}
