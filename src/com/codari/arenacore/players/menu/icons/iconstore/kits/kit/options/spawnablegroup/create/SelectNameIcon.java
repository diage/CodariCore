package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectNameIcon extends RequestIcon {
	
	public SelectNameIcon(Combatant combatant) {
		super(Material.OBSIDIAN, combatant, "Select Name");
	}

	@Override
	public String getConversationString() {
		return "Select a name for this random spawnable group.";
	}
}
