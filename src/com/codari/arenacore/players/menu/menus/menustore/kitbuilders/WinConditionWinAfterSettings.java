package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.winafter.SetFalseIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.winafter.SetTrueIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class WinConditionWinAfterSettings extends FunctionMenu {

	public WinConditionWinAfterSettings(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SetTrueIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new SetFalseIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
	
}
