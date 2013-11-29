
package com.codari.arenacore.players.menu.Icon.Interfaces;

import java.util.Map;

import com.codari.arenacore.players.menu.UtilityMenuSlot;
import com.codari.arenacore.players.menu.Icon.IconType;

/**
 * Interface which represents a menu icon, an icon that will change the buttons within the menu.
 * @author Diage, Soren025
 *
 */
public interface MenuIcon extends Icon {
	/**
	 * Method which returns a mapping of menu slots to the icon type which will be there upon clicking this icon.
	 * @return The map of {@link UtilityMenuSlot} to {@link IconType} which this icon will replace.
	 */
	public Map<UtilityMenuSlot, IconType> getMenuIcons();
	/**
	 * Method to create the icon for the player, this handles the display name and lore.
	 */
	public void createIcon();
	/**
	 * Method to set up the initial mapping.
	 */
	public void assignIcons();
}
