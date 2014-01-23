package com.codari.arenacore.players.menu.menus.menustore.help;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.help.ExampleExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.help.ExampleHoverIcon;
import com.codari.arenacore.players.menu.icons.iconstore.help.ExampleMenuIcon;
import com.codari.arenacore.players.menu.icons.iconstore.help.ExampleRequestIcon;
import com.codari.arenacore.players.menu.icons.iconstore.help.InstructionsIconFour;
import com.codari.arenacore.players.menu.icons.iconstore.help.InstructionsIconOne;
import com.codari.arenacore.players.menu.icons.iconstore.help.InstructionsIconThree;
import com.codari.arenacore.players.menu.icons.iconstore.help.InstructionsIconTwo;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class HelpMenu extends FunctionMenu {

	public HelpMenu(Combatant combatant) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.A_ONE, new InstructionsIconOne(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new InstructionsIconTwo(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new InstructionsIconThree(combatant));
		super.setSlot(FunctionMenuSlot.B_ONE, new InstructionsIconFour(combatant));
		super.setSlot(FunctionMenuSlot.B_TWO, new ExampleMenuIcon(combatant, new ExampleMenu(combatant, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.B_THREE, new ExampleExecutableIcon(combatant));
		super.setSlot(FunctionMenuSlot.B_FOUR, new ExampleHoverIcon(combatant));
		super.setSlot(FunctionMenuSlot.B_FIVE, new ExampleRequestIcon(combatant));
	}

}
