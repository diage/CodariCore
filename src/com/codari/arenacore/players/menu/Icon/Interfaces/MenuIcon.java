
package com.codari.arenacore.players.menu.Icon.Interfaces;

import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.UtilityMenu;

/**
 * Interface which represents a menu icon, an icon that will change the buttons within the menu.
 * @author Diage, Soren025
 *
 */
public interface MenuIcon extends Icon {
	public FunctionMenu getFunctionMenu();
	
	public UtilityMenu getUtilityMenu();
}
