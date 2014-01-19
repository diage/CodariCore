package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.WinConditionSelectionIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class WinConditionSelection extends FunctionMenu {
	private WinConditionSelection nextPage;
	private BackIcon backIcon;
	
	public WinConditionSelection(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		for(String winConditionName : ((LibraryCore)Codari.getLibrary()).getConditionNames()) {
			this.addWinConditionIcon(combatant, winConditionName);
		}
	}
	
	private WinConditionSelection(Combatant combatant, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}

	
	private void addWinConditionIcon(Combatant combatant, String winConditionName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new WinConditionSelectionIcon(combatant, new WinConditionOptions(combatant, winConditionName, new BackIcon(combatant, this)), winConditionName));
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
		this.nextPage = new WinConditionSelection(combatant, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
