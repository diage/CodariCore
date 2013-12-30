package com.codari.arenacore.players.menu.menus.menustore.utility;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.ExitIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.KitBuildersIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.KitsIcon;
import com.codari.arenacore.players.menu.menus.UtilityMenu;
import com.codari.arenacore.players.menu.menus.menustore.function.KitSelection;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;

public class UtilMenu extends UtilityMenu {

	public UtilMenu(Combatant combatant) {
		super(combatant);
		this.addIcons(combatant);
	}
	
	private void addIcons(Combatant combatant) {
		super.setSlot(UtilityMenuSlot.NINE, new ExitIcon(Material.OBSIDIAN, combatant));
		super.setSlot(UtilityMenuSlot.ONE, new KitsIcon(combatant, new KitSelection(combatant)));
		super.setSlot(UtilityMenuSlot.TWO, new KitBuildersIcon(combatant, new KitSelection(combatant)));	//FIXME
	}
}
