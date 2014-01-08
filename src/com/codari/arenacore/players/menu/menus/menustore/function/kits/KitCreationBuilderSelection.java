package com.codari.arenacore.players.menu.menus.menustore.function.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.builderselection.KitCreationBuilderIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitCreationBuilderSelection extends FunctionMenu {
	private KitCreationBuilderSelection nextPage;
	private BackIcon backIcon;
	
	public KitCreationBuilderSelection(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		for(String kitBuilderName : ((CombatantCore)combatant).getKitManager().getKitBuilders().keySet()) {
			this.addKitCreationBuilderIcon(combatant, kitBuilderName);
		}
		this.backIcon = backIcon;
		((CombatantCore)combatant).getKitManager().setKitCreationBuilderSelectionMenu(combatant, this);
	}
	
	private KitCreationBuilderSelection(Combatant combatant, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}

	
	public void addKitCreationBuilderIcon(Combatant combatant, String kitBuilderName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new KitCreationBuilderIcon(combatant, kitBuilderName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addKitCreationBuilderIcon(combatant, kitBuilderName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addKitCreationBuilderIcon(combatant, kitBuilderName);
			}
		}
	}

	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new KitCreationBuilderSelection(combatant, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}		
}
