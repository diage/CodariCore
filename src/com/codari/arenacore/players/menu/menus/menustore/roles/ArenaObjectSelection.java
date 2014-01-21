package com.codari.arenacore.players.menu.menus.menustore.roles;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.roles.creation.objects.ArenaObjectIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class ArenaObjectSelection extends FunctionMenu {
	private ArenaObjectSelection nextPage;
	private BackIcon backIcon;
	
	public ArenaObjectSelection(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		for(String arenaObjectName : ((LibraryCore) Codari.getLibrary()).getArenaObjectsWithLinks()) {
			this.addArenaObjectIcon(combatant, arenaObjectName);
		}
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
	}
	
	private ArenaObjectSelection(Combatant combatant, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
	}	
	
	private void addArenaObjectIcon(Combatant combatant, String arenaObjectName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new ArenaObjectIcon(combatant, new ArenaObjectLinkSelection(combatant, arenaObjectName, new BackIcon(combatant, this)), arenaObjectName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addArenaObjectIcon(combatant, arenaObjectName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addArenaObjectIcon(combatant, arenaObjectName);
			}
		}
	}	
	
	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new ArenaObjectSelection(combatant, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
