package com.codari.arenacore.players.menu.Icon.Interfaces;

import org.bukkit.entity.Player;
/**
 * Interface for information icons, icons which simply display particular bits of information.
 * @author Diage, Soren025
 *
 */
public interface InfoIcon extends Icon {
	/**
	 * Method to create the icon for the player, this handles the display name and lore.
	 * @param player The player which the icon will reference for information.
	 */
	public void createIcon(Player player);
}
