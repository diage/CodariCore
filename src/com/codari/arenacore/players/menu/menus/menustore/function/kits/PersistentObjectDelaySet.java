package com.codari.arenacore.players.menu.menus.menustore.function.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.SaveIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.UpdatePersistentDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.UpdatePersistentDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.UpdatePersistentDelayTicksIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class PersistentObjectDelaySet extends FunctionMenu {

	public PersistentObjectDelaySet(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon);
	}

	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new UpdatePersistentDelayMinutesIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new UpdatePersistentDelaySecondsIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new UpdatePersistentDelayTicksIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveIcon(combatant, kit));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
