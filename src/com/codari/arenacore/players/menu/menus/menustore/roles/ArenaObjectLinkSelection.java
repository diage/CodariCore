package com.codari.arenacore.players.menu.menus.menustore.roles;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.roles.creation.objects.LinkIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class ArenaObjectLinkSelection extends FunctionMenu {
	private ArenaObjectLinkSelection nextPage;
	private String arenaObjectName;
	private BackIcon backIcon;
	
	public ArenaObjectLinkSelection(Combatant combatant, String arenaObjectName, BackIcon backIcon) {
		super(combatant);
		this.arenaObjectName = arenaObjectName;
		for(String linkName : ((LibraryCore) Codari.getLibrary()).getLinks(this.arenaObjectName)) {
			this.addArenaObjectIcon(combatant, linkName);
		}
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
	}
	
	private ArenaObjectLinkSelection(Combatant combatant, String arenaObjectName, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.arenaObjectName = arenaObjectName;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
	}	
	
	private void addArenaObjectIcon(Combatant combatant, String linkName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new LinkIcon(combatant, this.arenaObjectName, linkName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addArenaObjectIcon(combatant, linkName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addArenaObjectIcon(combatant, linkName);
			}
		}
	}	
	
	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new ArenaObjectLinkSelection(combatant, this.arenaObjectName, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
