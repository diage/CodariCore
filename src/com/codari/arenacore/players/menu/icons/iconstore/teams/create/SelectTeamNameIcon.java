package com.codari.arenacore.players.menu.icons.iconstore.teams.create;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectTeamNameIcon extends RequestIcon {

	public SelectTeamNameIcon(Combatant combatant) {
		super(Material.GOLD_BLOCK, combatant, "Select Team Name");
	}

	@Override
	public String getConversationString() {
		return "Type in a name for the Team you would like to create.";
	}

}
