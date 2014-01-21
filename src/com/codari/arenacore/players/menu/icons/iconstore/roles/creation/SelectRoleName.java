package com.codari.arenacore.players.menu.icons.iconstore.roles.creation;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectRoleName extends RequestIcon {

	public SelectRoleName(Combatant combatant) {
		super(Material.STRING, combatant, "Select Role Name");
	}

	@Override
	public String getConversationString() {
		return "Type in a name for the Role.";
	}

}
