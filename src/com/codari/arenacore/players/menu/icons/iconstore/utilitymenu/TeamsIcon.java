package com.codari.arenacore.players.menu.icons.iconstore.utilitymenu;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class TeamsIcon extends MenuIcon {

	public TeamsIcon(Combatant combatant, Menu menu) {
		super(Material.CHEST, combatant, menu, "Team");
	}

}
