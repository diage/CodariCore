package com.codari.arenacore.players.menu.menus.menustore.kits;

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
	private Kit kit;
	
	public KitOptions(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.kit = kit;
		this.addIcons(combatant, backIcon);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new EditIcon(combatant, new SpawnableGroup(combatant, kit, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.A_TWO, new TurnOnToolbeltIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new TurnOffToolbeltIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_FOUR, new RenameIcon(combatant));	
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new ConstructIcon(combatant));
	}
}
