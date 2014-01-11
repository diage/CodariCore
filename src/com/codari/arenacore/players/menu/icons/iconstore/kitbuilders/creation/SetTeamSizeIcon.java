package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.HoverIcon;

public class SetTeamSizeIcon extends HoverIcon {

	public SetTeamSizeIcon(Combatant combatant) {
		super(Material.GLASS_BOTTLE, combatant, "Set Team Size");
	}

}
