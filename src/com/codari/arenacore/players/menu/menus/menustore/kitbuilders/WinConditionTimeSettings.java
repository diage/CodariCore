package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.timesettings.SaveWinConditionIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.timesettings.SetWinAfterTimeIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.timesettings.SetWinConditionMinuteIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.timesettings.SetWinConditionSecondIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.timesettings.SetWinConditionTickIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class WinConditionTimeSettings extends FunctionMenu {

	public WinConditionTimeSettings(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SetWinConditionMinuteIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_ONE, new SetWinConditionSecondIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_ONE, new SetWinConditionTickIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_ONE, new SetWinAfterTimeIcon(combatant, new WinConditionWinAfterSettings(combatant, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveWinConditionIcon(combatant));
	}
	
}
