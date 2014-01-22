package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.role.data.SetInfiniteRolesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.role.data.SetNumberOfRolesIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class RoleDataSettings extends FunctionMenu {

	public RoleDataSettings(Combatant combatant, String roleName, BackIcon backIcon) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.A_ONE, new SetNumberOfRolesIcon(combatant, roleName));
		super.setSlot(FunctionMenuSlot.A_TWO, new SetInfiniteRolesIcon(combatant, roleName));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}

}
