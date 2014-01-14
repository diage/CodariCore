package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot.RandomSpawnableSlotIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class RandomSlotSelection extends FunctionMenu {

	public RandomSlotSelection(Combatant combatant, String groupName, BackIcon backIcon) {
		super(combatant);
		this.addRandomSpawnableIcons(combatant, groupName, backIcon);
	}
	
	//---Random Spawnable Slot Selection Icons---//
	private void addRandomSpawnableIcons(Combatant combatant, String groupName, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		
		super.setSlot(FunctionMenuSlot.A_ONE, new RandomSpawnableSlotIcon(combatant, groupName, 0));
		super.setSlot(FunctionMenuSlot.A_TWO, new RandomSpawnableSlotIcon(combatant, groupName, 1));
		super.setSlot(FunctionMenuSlot.A_THREE, new RandomSpawnableSlotIcon(combatant, groupName, 2));
		super.setSlot(FunctionMenuSlot.A_FOUR, new RandomSpawnableSlotIcon(combatant, groupName, 3));
	}
	
}
