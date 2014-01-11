package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class WinConditionSelectionIcon extends MenuIcon {
	private String winConditionName;
	
	public WinConditionSelectionIcon(Combatant combatant, Menu menu, String winConditionName) {
		super(Material.DIAMOND_SWORD, combatant, menu, winConditionName);
		this.winConditionName = winConditionName;
	}
	
	public String getWinConditionName() {
		return this.winConditionName;
	}

}
