package com.codari.arenacore.players.menu.menus.menustore.function;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.SaveIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatTicksIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroupRepeatSet extends FunctionMenu {

	public SpawnableGroupRepeatSet(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon);
	}
	
	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new UpdateRandomRepeatMinutesIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new UpdateRandomRepeatSecondsIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new UpdateRandomRepeatTicksIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);	
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveIcon(combatant, kit));
	}	

}
