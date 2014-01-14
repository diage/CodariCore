package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot.PersistentObjectSlotIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class PersistentSlotSelection extends FunctionMenu {

	public PersistentSlotSelection(Combatant combatant, String arenaObjectName, BackIcon backIcon) {
		super(combatant);
		this.addPersistentIcons(combatant, arenaObjectName, backIcon);
	}

	
	//---Peristent Object Slot Selection Icons---//
	private void addPersistentIcons(Combatant combatant, String arenaObjectName, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		
		super.setSlot(FunctionMenuSlot.A_ONE, new PersistentObjectSlotIcon(combatant,arenaObjectName, 0));
		super.setSlot(FunctionMenuSlot.A_TWO, new PersistentObjectSlotIcon(combatant, arenaObjectName, 1));
		super.setSlot(FunctionMenuSlot.A_THREE, new PersistentObjectSlotIcon(combatant, arenaObjectName, 2));
		super.setSlot(FunctionMenuSlot.A_FOUR, new PersistentObjectSlotIcon(combatant, arenaObjectName, 3));
	}		
}
