package com.codari.arenacore.players.menu.icons.iconstore;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;

public class BorderIcon extends MenuIcon {
	public BorderIcon(Combatant combatant) {
		super(Material.BEDROCK, combatant, ChatColor.RED + "=====");
	}
}
