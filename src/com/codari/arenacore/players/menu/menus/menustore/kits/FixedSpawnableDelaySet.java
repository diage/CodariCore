package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.SaveIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.UpdateFixedDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.UpdateFixedDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.UpdateFixedDelayTicksIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class FixedSpawnableDelaySet extends FunctionMenu {

	public FixedSpawnableDelaySet(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon);
	}

	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new UpdateFixedDelayMinutesIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new UpdateFixedDelaySecondsIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new UpdateFixedDelayTicksIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveIcon(combatant, kit));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
