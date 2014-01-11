package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.settings.SelectWinConditionIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.settings.WinConditionIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class WinConditionSettings extends FunctionMenu {
	private WinConditionSettings nextPage;
	private BackIcon backIcon;
	
	public WinConditionSettings(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_THREE,  new SelectWinConditionIcon(combatant, new WinConditionSelection(combatant, new BackIcon(combatant, this))));
		((CombatantCore)combatant).getDynamicMenuManager().setWinConditionSettingsMenu(this);
	}
	
	private WinConditionSettings(Combatant combatant, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
		super.setSlot(FunctionMenuSlot.C_THREE,  new SelectWinConditionIcon(combatant, new WinConditionSelection(combatant, new BackIcon(combatant, this))));
	}

	
	public void addWinConditionIcon(Combatant combatant, String winConditionName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new WinConditionIcon(combatant, new WinConditionRemove(combatant, new BackIcon(combatant, this)), winConditionName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addWinConditionIcon(combatant, winConditionName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addWinConditionIcon(combatant, winConditionName);
			}
		}
	}

	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new WinConditionSettings(combatant, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
