package com.codari.arenacore.players.menu.icons.iconstore.teams.edit;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectPlayersIcon extends RequestIcon {

	public SelectPlayersIcon(Combatant combatant) {
		super(Material.MONSTER_EGGS, combatant, "Select Players");
	}

	@Override
	public String getConversationString() {
		return "Select the name of the player you would like to invite.";
	}

}
