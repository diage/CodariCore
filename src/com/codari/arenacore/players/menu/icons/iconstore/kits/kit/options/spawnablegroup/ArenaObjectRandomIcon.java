package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup;

import org.bukkit.Material;

import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaObjectRandomIcon extends MenuIcon {
	public ArenaObjectRandomIcon(Combatant combatant, Menu menu, RandomSpawnableObject arenaObject) {
		super(Material.MELON_BLOCK, combatant, menu, arenaObject.getClass().getSimpleName());
		// TODO Auto-generated constructor stub
	}
}
