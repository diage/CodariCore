package com.codari.arenacore.players.menu.icons.iconstore.teams.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class CreateTeamMenuIcon extends MenuIcon {

	public CreateTeamMenuIcon(Combatant combatant, Menu menu) {
		super(Material.EMERALD_BLOCK, combatant, menu, "Create Team");
	}

}
