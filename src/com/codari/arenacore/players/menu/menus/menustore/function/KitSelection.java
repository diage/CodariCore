package com.codari.arenacore.players.menu.menus.menustore.function;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.NewKitIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitSelection extends FunctionMenu {

	public KitSelection(Combatant combatant) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_THREE, new NewKitIcon(combatant, new KitCreation(combatant)));
	}
	
	public void addKit() {
		
	}
	
	public void removeKit() {
		
	}
}
