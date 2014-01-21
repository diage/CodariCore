package com.codari.arenacore.players.menu.icons.iconstore.roles.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class DeleteRoleIcon extends ExecutableIcon {

	public DeleteRoleIcon(Combatant combatant, String roleName) {
		super(Material.FIRE, combatant, "Delete this Role (TODO)");
	}

	@Override
	public void click() {
		
	}

}
