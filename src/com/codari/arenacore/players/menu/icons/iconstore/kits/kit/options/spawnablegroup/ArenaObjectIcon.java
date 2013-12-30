package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup;

import org.bukkit.Material;

import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaObjectIcon extends MenuIcon {

	public ArenaObjectIcon(Material material, Combatant combatant, Menu menu, ArenaObject arenaObject) {
		super(material, combatant, menu, arenaObject.getClass().getSimpleName());
		// TODO Auto-generated constructor stub
	}

}
