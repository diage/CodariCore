package com.codari.arenacore.players.menu.Abstracts;

import com.codari.arenacore.players.menu.MenuSlot;
import com.codari.arenacore.players.menu.Icon.Interfaces.Icon;


/**
 * Interface representing the physical menu within the player inventory.
 * 
 * Note that the first slot is the bottom left corner and the ninth slot is the upper right. 
 * @author Diage, Soren025
 *
 */
public interface Menu {
	/**
	 * Method to get the icon located at a particular slot.
	 * @param menuSlot The menu slot which the icon is sitting at.
	 * @return The icon located at the provided menu slot.
	 */
	public Icon getIcon(MenuSlot menuSlot);
	/**
	 * Method to manually set the icon at a given slot.
	 * @param menuSlot The menu slot to be set.
	 * @param icon The icon to set at the menu slot.
	 */
	public void setSlot(MenuSlot menuSlot, Icon icon);
}
