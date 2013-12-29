package com.codari.arenacore.players.menu.icons.iconstore.menu;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.menustore.utility.InitialUMenu;
/* 
 * Icon to display all the kits
 * */
public class KitsIcon extends MenuIcon {

	public KitsIcon(Combatant combatant) {
		super(Material.OBSIDIAN, combatant, new InitialUMenu(combatant), "Kits");
		// TODO Auto-generated constructor stub
	}

}
