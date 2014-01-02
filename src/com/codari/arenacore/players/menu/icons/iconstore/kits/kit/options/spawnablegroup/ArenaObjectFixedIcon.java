package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup;

import org.bukkit.Material;

import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaObjectFixedIcon extends MenuIcon {

	public ArenaObjectFixedIcon(Combatant combatant, Menu menu, FixedSpawnableObject arenaObject) {
		super(Material.POISONOUS_POTATO, combatant, menu, arenaObject.getClass().getSimpleName());
		// TODO Auto-generated constructor stub
	}

}
