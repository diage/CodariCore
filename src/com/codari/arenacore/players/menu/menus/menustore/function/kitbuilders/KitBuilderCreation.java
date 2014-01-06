package com.codari.arenacore.players.menu.menus.menustore.function.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitBuilderCreation extends FunctionMenu {

	public KitBuilderCreation(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	} 
}
