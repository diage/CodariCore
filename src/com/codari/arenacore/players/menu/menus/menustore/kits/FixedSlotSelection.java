package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot.FixedSpawnableSlotIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class FixedSlotSelection extends FunctionMenu {

	public FixedSlotSelection(Combatant combatant, String arenaObjectName, BackIcon backIcon) {
		super(combatant);
		this.addFixedSpawnableIcons(combatant, arenaObjectName, backIcon);
	}
	
	
	//---Fixed Spawnable Slot Selection Icons---//
	private void addFixedSpawnableIcons(Combatant combatant, String arenaObjectName, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		
		super.setSlot(FunctionMenuSlot.A_ONE, new FixedSpawnableSlotIcon(combatant, arenaObjectName, 0));
		super.setSlot(FunctionMenuSlot.A_TWO, new FixedSpawnableSlotIcon(combatant, arenaObjectName, 1));
		super.setSlot(FunctionMenuSlot.A_THREE, new FixedSpawnableSlotIcon(combatant, arenaObjectName, 2));
		super.setSlot(FunctionMenuSlot.A_FOUR, new FixedSpawnableSlotIcon(combatant, arenaObjectName, 3));
	}	
}
