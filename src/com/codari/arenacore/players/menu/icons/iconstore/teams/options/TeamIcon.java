package com.codari.arenacore.players.menu.icons.iconstore.teams.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class TeamIcon extends MenuIcon {

	public TeamIcon(Combatant combatant, Menu menu, String teamName) {
		super(Material.DIAMOND_BLOCK, combatant, menu, teamName);
	}

}
