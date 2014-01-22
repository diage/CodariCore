package com.codari.arenacore.players.menu.menus.menustore.help;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class ExampleMenu extends FunctionMenu {

	public ExampleMenu(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}

}
