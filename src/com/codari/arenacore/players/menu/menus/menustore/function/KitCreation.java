package com.codari.arenacore.players.menu.menus.menustore.function;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.SaveKitIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.SelectKitBuilderIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.SelectKitNameIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitCreation extends FunctionMenu {

	public KitCreation(Combatant combatant) {
		super(combatant);
		this.addIcons(combatant);
	}
	
	private void addIcons(Combatant combatant) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SelectKitNameIcon(Material.DIAMOND_BLOCK, combatant, "Select Name"));
		super.setSlot(FunctionMenuSlot.A_TWO, new SelectKitBuilderIcon(combatant, new KitBuilderSelection(combatant)));
		super.setSlot(FunctionMenuSlot.C_ONE, new BackIcon(combatant, new KitSelection(combatant)));
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveKitIcon(combatant));
	} 
}
