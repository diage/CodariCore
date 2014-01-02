package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class OverrideIcon extends RequestIcon {	
	public OverrideIcon(Combatant combatant) {
		super(Material.ANVIL, combatant, "Set Override (Optional)");
	}

	@Override
	public String getConversationString() {
		return "Type in true or false for the override.";
	}
}
