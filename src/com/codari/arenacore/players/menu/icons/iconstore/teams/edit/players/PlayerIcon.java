package com.codari.arenacore.players.menu.icons.iconstore.teams.edit.players;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class PlayerIcon extends MenuIcon {

	public PlayerIcon(Combatant combatant, Menu menu, String playerName) {
		super(Material.INK_SACK, combatant, menu, playerName);
	}

}
