package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot.SpawnPointSlotIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnPointSlotSlection extends FunctionMenu {

	public SpawnPointSlotSlection(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addSpawnPointIcons(combatant, backIcon);
	}

	//---Spawn Point Slot Selection Icons---//
	private void addSpawnPointIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		
		super.setSlot(FunctionMenuSlot.A_ONE, new SpawnPointSlotIcon(combatant, 0));
		super.setSlot(FunctionMenuSlot.A_TWO, new SpawnPointSlotIcon(combatant, 1));
		super.setSlot(FunctionMenuSlot.A_THREE, new SpawnPointSlotIcon(combatant, 2));
		super.setSlot(FunctionMenuSlot.A_FOUR, new SpawnPointSlotIcon(combatant, 3));
	}
	
}
