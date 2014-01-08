package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.selection.KitBuilderIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.selection.NewKitBuilderIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitBuilderSelection extends FunctionMenu {
	private KitBuilderSelection nextPage;
	
	/**
	 *  Construct the first page for a Kit Builder Selection Menu.
	 *  
	 *  @param Combatant reference
	 *  
	 *   */
	public KitBuilderSelection(Combatant combatant) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_THREE, new NewKitBuilderIcon(combatant, new KitBuilderCreation(combatant, new BackIcon(combatant, this))));
		for(String kitBuilderName : ((CombatantCore)combatant).getKitManager().getKitBuilders().keySet()) {
			this.addKitBuilderIcon(combatant, kitBuilderName);
		}
		((CombatantCore)combatant).getKitManager().setKitBuilderSelectionMenu(combatant, this);
	}
	
	/* This will construct any further needed pages for Kit Selection. */
	private KitBuilderSelection(Combatant combatant, Icon previous) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, previous);
		super.setSlot(FunctionMenuSlot.C_THREE, new NewKitBuilderIcon(combatant, new KitBuilderCreation(combatant, new BackIcon(combatant, this))));
	}	
	
	public void addKitBuilderIcon(Combatant combatant, String kitBuilderName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new KitBuilderIcon(combatant, new KitBuilderOptions(combatant, ((CombatantCore)combatant).getKitManager().getKitBuilder(kitBuilderName), new BackIcon(combatant, this)), kitBuilderName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addKitBuilderIcon(combatant, kitBuilderName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addKitBuilderIcon(combatant, kitBuilderName);
			}
		}
	}	
	
	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new KitBuilderSelection(combatant, prevIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
