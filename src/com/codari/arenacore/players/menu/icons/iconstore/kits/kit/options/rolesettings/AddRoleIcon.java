package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.rolesettings;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;
import com.codari.arenacore.players.menu.menus.menustore.kits.RoleSettings;

public class AddRoleIcon extends MenuIcon {
	private RoleSettings roleSettings;
	
	public AddRoleIcon(Combatant combatant, Menu menu, RoleSettings roleSettings) {
		super(Material.STONE_PICKAXE, combatant, menu, "Add Role");
		this.roleSettings = roleSettings;
	}

	public RoleSettings getRoleSettings() {
		return this.roleSettings;
	}
}
