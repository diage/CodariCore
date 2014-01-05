package com.codari.arenacore.players.menu.menus.menustore.function.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.ConstructIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.EditIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.RenameIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.TurnOffToolbeltIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.TurnOnToolbeltIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitOptions extends FunctionMenu {

	public KitOptions(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon);
	}

	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new EditIcon(combatant, new SpawnableGroup(combatant, kit, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.A_TWO, new ConstructIcon(combatant, kit.getName()));
		super.setSlot(FunctionMenuSlot.A_FOUR, new RenameIcon(combatant, kit));	
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_THREE, new TurnOnToolbeltIcon(combatant, kit.getName()));
		super.setSlot(FunctionMenuSlot.C_FOUR, new TurnOffToolbeltIcon(combatant));
	}
}
