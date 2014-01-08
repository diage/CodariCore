package com.codari.arenacore.players.menu.menus.menustore.function.kits;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.SaveKitIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.SelectKitBuilderIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.SelectKitNameIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitCreation extends FunctionMenu {

	public KitCreation(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}
	
	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SelectKitNameIcon(Material.DIAMOND_BLOCK, combatant, "Select Name"));
		super.setSlot(FunctionMenuSlot.A_TWO, new SelectKitBuilderIcon(combatant, new KitBuilderSelection(combatant, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveKitIcon(combatant));
	} 
}