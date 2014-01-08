package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroupEdit extends FunctionMenu {

	public SpawnableGroupEdit(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}
	
	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
