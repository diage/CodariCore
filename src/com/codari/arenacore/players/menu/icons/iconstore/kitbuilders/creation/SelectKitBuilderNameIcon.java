package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectKitBuilderNameIcon extends RequestIcon{

	public SelectKitBuilderNameIcon(Combatant combatant) {
		super(Material.DIAMOND_BLOCK, combatant, "Select Kit Builder Name");
	}

	@Override
	public String getConversationString() {
		return "Type in the name of the Kit Builder you would like to create.";
	}

}
