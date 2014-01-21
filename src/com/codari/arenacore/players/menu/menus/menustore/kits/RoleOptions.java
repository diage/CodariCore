package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.rolesettings.DeleteArenaRoleIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class RoleOptions extends FunctionMenu {

	public RoleOptions(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.A_ONE, new DeleteArenaRoleIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}

}
