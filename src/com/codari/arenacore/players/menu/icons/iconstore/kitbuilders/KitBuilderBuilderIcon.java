package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class KitBuilderBuilderIcon extends RequestIcon {

	public KitBuilderBuilderIcon(Combatant combatant) {
		super(Material.REDSTONE_BLOCK, combatant, "Create Kit Builder");
	}

	@Override
	public String getConversationString() {
		return "Type in the name of the Kit Builder you would like to create.";
	}
}
