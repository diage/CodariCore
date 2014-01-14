package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.SaveIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.UpdateFixedRepeatMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.UpdateFixedRepeatSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.UpdateFixedRepeatTicksIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class FixedSpawnableRepeatSet extends FunctionMenu {

	public FixedSpawnableRepeatSet(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new UpdateFixedRepeatMinutesIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new UpdateFixedRepeatSecondsIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new UpdateFixedRepeatTicksIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
