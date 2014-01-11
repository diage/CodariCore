package com.codari.arenacore.players.menu.icons.iconstore.teams.edit;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectPlayerIcon extends RequestIcon {

	public SelectPlayerIcon(Combatant combatant) {
		super(Material.MONSTER_EGGS, combatant, "Select Player To Invite");
	}

	@Override
	public String getConversationString() {
		return "Type in the name of the player you would like to invite.";
	}

}
