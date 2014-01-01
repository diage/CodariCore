package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class OverrideIcon extends RequestIcon {	
	public OverrideIcon(Material material, Combatant combatant, String kitName) {
		super(material, combatant, "Set Override");
	}

	@Override
	public String getConversationString() {
		return "Type in true or false for the override.";
	}
}
