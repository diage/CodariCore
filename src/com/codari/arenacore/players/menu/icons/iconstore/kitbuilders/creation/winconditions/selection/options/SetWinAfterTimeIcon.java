package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class SetWinAfterTimeIcon extends MenuIcon {

	public SetWinAfterTimeIcon(Combatant combatant, Menu menu) {
		super(Material.BONE, combatant, menu, "Set Win After Win Condition Time");
	}

}
