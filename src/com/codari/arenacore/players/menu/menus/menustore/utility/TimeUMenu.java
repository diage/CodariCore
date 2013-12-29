package com.codari.arenacore.players.menu.menus.menustore.utility;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.ExitIcon;
import com.codari.arenacore.players.menu.menus.UtilityMenu;
import com.codari.arenacore.players.menu.menus.menustore.function.TeamSizeFMenu;
import com.codari.arenacore.players.menu.menus.menustore.function.WinConditionFMenu;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;

public class TimeUMenu extends UtilityMenu {

	public TimeUMenu(Combatant combatant) {
		super(combatant);
		this.setSlot(UtilityMenuSlot.ONE, new MenuIcon(Material.APPLE, combatant, new InitialUMenu(combatant), "Back"));
		this.setSlot(UtilityMenuSlot.THREE, new ExitIcon(Material.QUARTZ_ORE, combatant, "Cancel"));
		this.setSlot(UtilityMenuSlot.SEVEN, new MenuIcon(Material.BAKED_POTATO, combatant, new TeamSizeFMenu(), 
					new TeamSizeUMenu(combatant), "Goto Set Team Size"));
		this.setSlot(UtilityMenuSlot.NINE, new MenuIcon(Material.BLAZE_POWDER, combatant, new WinConditionFMenu(),
					new WinConditionUMenu(combatant), "Proceed..."));
	}
}
