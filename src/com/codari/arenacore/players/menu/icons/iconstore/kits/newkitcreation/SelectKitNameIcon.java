package com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;
/* Note: This Icon is handled within the SaveKitIcon class. */
public class SelectKitNameIcon extends RequestIcon {

	public SelectKitNameIcon(Material material, Combatant combatant, String displayName) {
		super(material, combatant, displayName);
	}

	@Override
	public String getConversationString() {
		return "Select a name for the kit.";
	}
}
