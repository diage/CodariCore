package com.codari.arenacore.players.menu.menus.menustore.function;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroup extends FunctionMenu {

	public SpawnableGroup(Combatant combatant, Kit kit) {
		super(combatant);
		this.addIcons(combatant, kit);
	}

	private void addIcons(Combatant combatant, Kit kit) {
		super.setSlot(FunctionMenuSlot.C_ONE, new BackIcon(combatant, new KitOptions(combatant, kit)));
	}
}
