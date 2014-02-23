package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.objects.SpawnPoint;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaObjectSpawnPointIcon extends MenuIcon {

	public ArenaObjectSpawnPointIcon(Combatant combatant, Menu menu) {
		super(Material.BRICK_STAIRS, combatant, menu, SpawnPoint.SPAWN_POINT_NAME);
	}

}
