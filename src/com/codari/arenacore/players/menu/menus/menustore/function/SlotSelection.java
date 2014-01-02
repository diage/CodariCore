package com.codari.arenacore.players.menu.menus.menustore.function;

import com.codari.api5.util.Time;
import com.codari.arena5.objects.persistant.PersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot.FixedSpawnableSlotIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot.PersistentObjectSlotIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot.RandomSpawnableSlotIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SlotSelection extends FunctionMenu {

	public SlotSelection(Combatant combatant, Kit kit, String groupName, RandomSpawnableObject randomSpawnableObject, BackIcon backIcon) {
		super(combatant);
		this.addRandomSpawnableIcons(combatant, kit, groupName, randomSpawnableObject, backIcon);
	}
	
	public SlotSelection(Combatant combatant, Kit kit, FixedSpawnableObject fixedSpawnableObject, Time delayTime, Time repeatTime, BackIcon backIcon) {
		super(combatant);
		this.addFixedSpawnableIcons(combatant, kit, fixedSpawnableObject, delayTime, repeatTime, backIcon);
	}
	
	public SlotSelection(Combatant combatant, Kit kit, PersistentObject persistentObject, Time delayTime, boolean override, BackIcon backIcon) {
		super(combatant);
		this.addPersistentIcons(combatant, kit, persistentObject, delayTime, override, backIcon);
	}
	
	//---Random Spawnable Slot Selection Icons---//
	private void addRandomSpawnableIcons(Combatant combatant, Kit kit, String groupName, RandomSpawnableObject randomSpawnableObject, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		
		super.setSlot(FunctionMenuSlot.A_ONE, new RandomSpawnableSlotIcon(combatant, kit, groupName, randomSpawnableObject, 1));
		super.setSlot(FunctionMenuSlot.A_TWO, new RandomSpawnableSlotIcon(combatant, kit, groupName, randomSpawnableObject, 2));
		super.setSlot(FunctionMenuSlot.A_THREE, new RandomSpawnableSlotIcon(combatant, kit, groupName, randomSpawnableObject, 3));
		super.setSlot(FunctionMenuSlot.A_FOUR, new RandomSpawnableSlotIcon(combatant, kit, groupName, randomSpawnableObject, 4));
		super.setSlot(FunctionMenuSlot.A_FIVE, new RandomSpawnableSlotIcon(combatant, kit, groupName, randomSpawnableObject, 5));
		super.setSlot(FunctionMenuSlot.B_THREE, new RandomSpawnableSlotIcon(combatant, kit, groupName, randomSpawnableObject, 6));
	}
	
	//---Fixed Spawnable Slot Selection Icons---//
	private void addFixedSpawnableIcons(Combatant combatant, Kit kit, FixedSpawnableObject fixedSpawnableObject, Time delayTime, Time repeatTime, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		
		super.setSlot(FunctionMenuSlot.A_ONE, new FixedSpawnableSlotIcon(combatant, kit, fixedSpawnableObject, delayTime, repeatTime, 1));
		super.setSlot(FunctionMenuSlot.A_TWO, new FixedSpawnableSlotIcon(combatant, kit, fixedSpawnableObject, delayTime, repeatTime, 2));
		super.setSlot(FunctionMenuSlot.A_THREE, new FixedSpawnableSlotIcon(combatant, kit, fixedSpawnableObject, delayTime, repeatTime, 3));
		super.setSlot(FunctionMenuSlot.A_FOUR, new FixedSpawnableSlotIcon(combatant, kit, fixedSpawnableObject, delayTime, repeatTime, 4));
		super.setSlot(FunctionMenuSlot.A_FIVE, new FixedSpawnableSlotIcon(combatant, kit, fixedSpawnableObject, delayTime, repeatTime, 5));
		super.setSlot(FunctionMenuSlot.B_THREE, new FixedSpawnableSlotIcon(combatant, kit, fixedSpawnableObject, delayTime, repeatTime, 6));
	}
	
	//---Peristent Object Slot Selection Icons---//
	private void addPersistentIcons(Combatant combatant, Kit kit, PersistentObject persistentObject, Time delayTime, boolean override, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		
		super.setSlot(FunctionMenuSlot.A_ONE, new PersistentObjectSlotIcon(combatant, kit, persistentObject, delayTime, override, 1));
		super.setSlot(FunctionMenuSlot.A_TWO, new PersistentObjectSlotIcon(combatant, kit, persistentObject, delayTime, override, 2));
		super.setSlot(FunctionMenuSlot.A_THREE, new PersistentObjectSlotIcon(combatant, kit, persistentObject, delayTime, override, 3));
		super.setSlot(FunctionMenuSlot.A_FOUR, new PersistentObjectSlotIcon(combatant, kit, persistentObject, delayTime, override, 4));
		super.setSlot(FunctionMenuSlot.A_FIVE, new PersistentObjectSlotIcon(combatant, kit, persistentObject, delayTime, override, 5));
		super.setSlot(FunctionMenuSlot.B_THREE, new PersistentObjectSlotIcon(combatant, kit, persistentObject, delayTime, override, 6));
	}	
}
