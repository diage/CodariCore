package com.codari.arenacore.players.menu.icons.iconstore.teams.edit;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class CheckPlayersIcon extends MenuIcon {

	public CheckPlayersIcon(Combatant combatant, Menu menu) {
		super(Material.CHAINMAIL_CHESTPLATE, combatant, menu, "Players");
	}

}
