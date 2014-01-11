package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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

public class TimedActionSelection extends FunctionMenu {
	private TimedActionSelection nextPage;
	private BackIcon backIcon;
	
	public TimedActionSelection(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		for(String timedActionName : ((LibraryCore)Codari.getLibrary()).getActionNames()) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "Adding Timed Action to Menu!"); //TODO - for testing
			this.addTimedActionIcon(combatant, timedActionName);
		}
	}
	
	private TimedActionSelection(Combatant combatant, Icon previous, BackIcon backIcon) {
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
		this.nextPage = new TimedActionSelection(combatant, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
