package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.SetSlotIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.SetDelayTimeIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.OverrideIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class PersistentObjectSettings extends FunctionMenu {

	public PersistentObjectSettings(Combatant combatant, String arenaObjectName, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, arenaObjectName, backIcon);
	}

	private void addIcons(Combatant combatant, String arenaObjectName, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SetDelayTimeIcon(combatant, new PersistentObjectDelaySet(combatant, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.A_TWO, new OverrideIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new SetSlotIcon(combatant, new PersistentSlotSelection(combatant, arenaObjectName, new BackIcon(combatant, this))));
	}
}
