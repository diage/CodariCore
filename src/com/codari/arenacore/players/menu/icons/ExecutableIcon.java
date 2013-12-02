package com.codari.arenacore.players.menu.icons;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.icons.structure.IconType;

public abstract class ExecutableIcon extends Icon {

	public ExecutableIcon(Material material, Player player, String displayName) {
		super(material, player, IconType.EXECUTABLE, displayName);
	}
	
	public abstract void click();
}
