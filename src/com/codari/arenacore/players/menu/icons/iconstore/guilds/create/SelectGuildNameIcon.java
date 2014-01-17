package com.codari.arenacore.players.menu.icons.iconstore.guilds.create;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectGuildNameIcon extends RequestIcon {

	public SelectGuildNameIcon(Combatant combatant) {
		super(Material.GOLD_BLOCK, combatant, "Select Guild Name");
	}

	@Override
	public String getConversationString() {
		return "Type in a name for the Guild you would like to create.";
	}
	
}
