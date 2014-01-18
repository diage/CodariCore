package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.SaveWinConditionIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.SetWinAfterTimeIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.SetWinConditionArgumentsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.SetWinConditionMinuteIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.SetWinConditionSecondIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.SetWinConditionTickIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class WinConditionOptions extends FunctionMenu {

	public WinConditionOptions(Combatant combatant, String winConditionName, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, winConditionName, backIcon);
	}

	private void addIcons(Combatant combatant, String winConditionName, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SetWinConditionMinuteIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new SetWinConditionSecondIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new SetWinConditionTickIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_FOUR, new SetWinAfterTimeIcon(combatant, new WinConditionWinAfterSettings(combatant, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.A_FIVE, new SetWinConditionArgumentsIcon(combatant, new WinConditionArguments(combatant, winConditionName, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveWinConditionIcon(combatant));
	}
	
}
