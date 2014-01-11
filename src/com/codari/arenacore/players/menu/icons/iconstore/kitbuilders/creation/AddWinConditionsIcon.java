package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class AddWinConditionsIcon extends MenuIcon {

	public AddWinConditionsIcon(Combatant combatant, Menu menu) {
		super(Material.QUARTZ, combatant, menu, "Win Condition Settings");
	}

}
