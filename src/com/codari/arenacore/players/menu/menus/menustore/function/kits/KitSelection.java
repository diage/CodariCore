package com.codari.arenacore.players.menu.menus.menustore.function.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.KitIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.NewKitIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitSelection extends FunctionMenu {
	private KitSelection nextPage;
	
	/**
	 *  Construct the first page for a Kit Selection Menu.
	 *  
	 *  @param Combatant reference
	 *  
	 *   */
	public KitSelection(Combatant combatant) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_THREE, new NewKitIcon(combatant, new KitCreation(combatant, new BackIcon(combatant, this))));
		for(String kitName : ((CombatantCore)combatant).getKitManager().getKits().keySet()) {
			this.addKitIcon(combatant, kitName);
		}
		((CombatantCore)combatant).getKitManager().setKitSelectionMenu(combatant, this);
	}
	
	/* This will construct any further needed pages for Kit Selection. */
	private KitSelection(Combatant combatant, Icon previous) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, previous);
		super.setSlot(FunctionMenuSlot.C_THREE, new NewKitIcon(combatant, new KitCreation(combatant, new BackIcon(combatant, this))));
	}	
	
	public void addKitIcon(Combatant combatant, String kitName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new KitIcon(combatant, new KitOptions(combatant, ((CombatantCore)combatant).getKitManager().getKit(kitName), new BackIcon(combatant, this)), kitName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addKitIcon(combatant, kitName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addKitIcon(combatant, kitName);
			}
		}
	}	
	
	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new KitSelection(combatant, prevIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
