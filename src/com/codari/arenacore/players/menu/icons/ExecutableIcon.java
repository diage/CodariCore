package com.codari.arenacore.players.menu.icons;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.icons.structure.IconType;

public class ExecutableIcon extends Icon {

	public ExecutableIcon(Material material, Player player) {
		super(material, player, IconType.EXECUTABLE);
	}

}
