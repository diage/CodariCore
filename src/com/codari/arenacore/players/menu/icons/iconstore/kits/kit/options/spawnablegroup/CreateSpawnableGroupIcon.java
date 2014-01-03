package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class CreateSpawnableGroupIcon extends MenuIcon {

	public CreateSpawnableGroupIcon(Combatant combatant, Menu menu) {
		super(Material.GOLD_BLOCK, combatant, menu, "Create Spawnable Group");
	}
}
