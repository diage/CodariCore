package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ArenaObjectRandomIcon extends MenuIcon {
	private String randomArenaObjectName;
	
	public ArenaObjectRandomIcon(Combatant combatant, Menu menu, String arenaObjectName) {
		super(Material.MELON_BLOCK, combatant, menu, arenaObjectName);
		this.randomArenaObjectName = arenaObjectName;
	}
	
	public String getRandomArenaObjectName() {
		return this.randomArenaObjectName;
	}
}
