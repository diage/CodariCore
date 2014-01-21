package com.codari.arenacore.players.menu.menus.menustore.roles;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.roles.options.DeleteRoleIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class RoleOptions extends FunctionMenu {

	public RoleOptions(Combatant combatant, Role role, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, role, backIcon);
	}

	private void addIcons(Combatant combatant, Role role, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new DeleteRoleIcon(combatant, role.getName()));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
