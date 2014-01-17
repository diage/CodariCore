package com.codari.arenacore.players.menu.icons.iconstore.guilds.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class CreateGuildMenuIcon extends MenuIcon {

	public CreateGuildMenuIcon(Combatant combatant, Menu menu) {
		super(Material.GOLD_CHESTPLATE, combatant, menu, "Create Guild");
	}

}
