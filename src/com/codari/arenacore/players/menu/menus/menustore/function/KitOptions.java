package com.codari.arenacore.players.menu.menus.menustore.function;

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

	public KitOptions(Combatant combatant, Kit kit) {
		super(combatant);
		this.addIcons(combatant, kit);
	}

	private void addIcons(Combatant combatant, Kit kit) {
		super.setSlot(FunctionMenuSlot.A_ONE, new EditIcon(combatant, new SpawnableGroup(combatant, kit)));
		super.setSlot(FunctionMenuSlot.A_TWO, new ConstructIcon(combatant, kit.getName()));
		//super.setSlot(FunctionMenuSlot.A_THREE, new DeleteIcon(combatant)); //FIXME - needs to be able to delete the current kit and return to the kit selection menu
		super.setSlot(FunctionMenuSlot.A_FOUR, new RenameIcon(combatant, kit));	
		super.setSlot(FunctionMenuSlot.C_ONE, new BackIcon(combatant, new KitSelection(combatant)));
		super.setSlot(FunctionMenuSlot.C_THREE, new TurnOnToolbeltIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_FOUR, new TurnOffToolbeltIcon(combatant));
	}
}
