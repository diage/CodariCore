package com.codari.arenacore.players.menu.menus.menustore.utility;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.icons.iconstore.executable.CancelIcon;
import com.codari.arenacore.players.menu.menus.UtilityMenu;
import com.codari.arenacore.players.menu.menus.menustore.function.TeamSizeFMenu;
import com.codari.arenacore.players.menu.menus.menustore.function.WinConditionFMenu;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;

public class TimeUMenu extends UtilityMenu {

	public TimeUMenu(Player player) {
		super(player);
		this.setSlot(UtilityMenuSlot.ONE, new MenuIcon(Material.APPLE, player, new InitialUMenu(player), "Back"));
		this.setSlot(UtilityMenuSlot.THREE, new CancelIcon(Material.QUARTZ_ORE, player, "Cancel"));
		this.setSlot(UtilityMenuSlot.SEVEN, new MenuIcon(Material.BAKED_POTATO, player, new TeamSizeFMenu(), 
					new TeamSizeUMenu(player), "Goto Set Team Size"));
		this.setSlot(UtilityMenuSlot.NINE, new MenuIcon(Material.BLAZE_POWDER, player, new WinConditionFMenu(),
					new WinConditionUMenu(player), "Proceed..."));
	}

}
