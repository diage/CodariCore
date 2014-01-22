package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaObjectRoleSelectionIcon extends MenuIcon {

	public ArenaObjectRoleSelectionIcon(Combatant combatant, Menu menu, String roleSelectionObjectName) {
		super(Material.STAINED_CLAY, combatant, menu, roleSelectionObjectName);
	}

}
