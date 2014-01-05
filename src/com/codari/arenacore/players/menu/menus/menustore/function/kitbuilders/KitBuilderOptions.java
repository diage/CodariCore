package com.codari.arenacore.players.menu.menus.menustore.function.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.KitBuilder;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;

public class KitBuilderOptions extends FunctionMenu {

	public KitBuilderOptions(Combatant combatant, KitBuilder kitBuilder, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kitBuilder, backIcon);
	}

	private void addIcons(Combatant combatant, KitBuilder kitBuilder, BackIcon backIcon) {
		
	}
}
