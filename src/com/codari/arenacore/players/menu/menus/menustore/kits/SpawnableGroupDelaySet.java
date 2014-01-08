package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.SaveIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelayTicksIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroupDelaySet extends FunctionMenu {

	public SpawnableGroupDelaySet(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon);
	}
	
	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new UpdateRandomDelayMinutesIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new UpdateRandomDelaySecondsIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new UpdateRandomDelayTicksIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);	
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveIcon(combatant, kit));
	}
}
