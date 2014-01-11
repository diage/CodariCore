package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.timedactions.selection.TimedActionAddIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class TimedActionSelect extends FunctionMenu {
	private TimedActionSelect nextPage;
	private BackIcon backIcon;
	
	public TimedActionSelect(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		for(String timedActionName : ((LibraryCore)Codari.getLibrary()).getActionNames()) {
			this.addTimedActionIcon(combatant, timedActionName);
		}
	}
	
	private TimedActionSelect(Combatant combatant, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}

	
	private void addTimedActionIcon(Combatant combatant, String winConditionName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new TimedActionAddIcon(combatant, winConditionName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addTimedActionIcon(combatant, winConditionName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addTimedActionIcon(combatant, winConditionName);
			}
		}
	}

	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new TimedActionSelect(combatant, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
