package com.codari.arenacore.players.menu.icons;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.icons.structure.IconType;

public abstract class ExecutableIcon extends Icon {

	public ExecutableIcon(Material material, Combatant combatant, String displayName) {
		super(material, combatant, IconType.EXECUTABLE, displayName);
	}
	
	public abstract void click();
}
