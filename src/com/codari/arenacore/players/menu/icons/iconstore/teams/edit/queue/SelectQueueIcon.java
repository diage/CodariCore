package com.codari.arenacore.players.menu.icons.iconstore.teams.edit.queue;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectQueueIcon extends RequestIcon {

	public SelectQueueIcon(Combatant combatant) {
		super(Material.VINE, combatant, "Select Arena");
	}

	@Override
	public String getConversationString() {
		return "Type in the name of the Arena you would like to join.";
	}

}
