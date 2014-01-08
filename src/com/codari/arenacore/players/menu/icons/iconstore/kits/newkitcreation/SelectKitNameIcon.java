package com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;
/* Note: This Icon is handled within the SaveKitIcon class. */
public class SelectKitNameIcon extends RequestIcon {

	public SelectKitNameIcon(Combatant combatant) {
		super(Material.DIAMOND_BLOCK, combatant, "Select Kit Name");
	}

	@Override
	public String getConversationString() {
		return "Type a name for the Kit.";
	}
}
