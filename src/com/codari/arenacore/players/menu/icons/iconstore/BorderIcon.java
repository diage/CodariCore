package com.codari.arenacore.players.menu.icons.iconstore;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.icons.MenuIcon;

public class BorderIcon extends MenuIcon {
	public BorderIcon(Player player) {
		super(Material.BEDROCK, player, ChatColor.RED + "=====");
	}
}
