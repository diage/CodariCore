package com.codari.arenacore.players.menu.menus.menustore.help;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.help.ExampleExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.help.ExampleHoverIcon;
import com.codari.arenacore.players.menu.icons.iconstore.help.ExampleMenuIcon;
import com.codari.arenacore.players.menu.icons.iconstore.help.ExampleRequestIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class HelpMenu extends FunctionMenu {

	public HelpMenu(Combatant combatant) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.A_ONE, new ExampleMenuIcon(combatant, new ExampleMenu(combatant, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.A_TWO, new ExampleExecutableIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new ExampleHoverIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_FOUR, new ExampleRequestIcon(combatant));
	}

}
