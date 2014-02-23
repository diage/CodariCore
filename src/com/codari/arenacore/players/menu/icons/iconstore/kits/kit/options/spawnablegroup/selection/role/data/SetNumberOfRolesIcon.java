package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.role.data;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.HoverIcon;

public class SetNumberOfRolesIcon extends HoverIcon {
	private String roleName;
	
	public SetNumberOfRolesIcon(Combatant combatant, String roleName) {
		super(Material.SUGAR_CANE, combatant, "Set Number Of Roles");
		this.roleName = roleName;
	}

	public String getRoleName() {
		return this.roleName;
	}
}
