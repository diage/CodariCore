package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.KitBuilder;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.options.DeleteKitBuilderIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitBuilderOptions extends FunctionMenu {

	public KitBuilderOptions(Combatant combatant, KitBuilder kitBuilder, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kitBuilder, backIcon);
	}

	private void addIcons(Combatant combatant, KitBuilder kitBuilder, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new DeleteKitBuilderIcon(combatant, kitBuilder.getName()));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
