package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.timedactions.settings.SelectTimedActionIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.timedactions.settings.TimedActionIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class TimedActionSettings extends FunctionMenu {
	private TimedActionSettings nextPage;
	private BackIcon backIcon;
	
	public TimedActionSettings(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FOUR, new SelectTimedActionIcon(combatant, new TimedActionSelect(combatant, new BackIcon(combatant, this))));
		((CombatantCore)combatant).getDynamicMenuManager().setTimedActionSettingsMenu(this);
	}
	
	private TimedActionSettings(Combatant combatant, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}

	
	public void addTimedActionIcon(Combatant combatant, String timedActionName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new TimedActionIcon(combatant, new TimedActionRemove(combatant, new BackIcon(combatant, this)), timedActionName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addTimedActionIcon(combatant, timedActionName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addTimedActionIcon(combatant, timedActionName);
			}
		}
	}

	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new TimedActionSettings(combatant, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
