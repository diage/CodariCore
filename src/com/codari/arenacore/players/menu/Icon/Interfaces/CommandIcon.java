package com.codari.arenacore.players.menu.Icon.Interfaces;

/**
 * Interface for command icons, an icon which will issue a command upon clicking.
 * @author Diage, Soren025
 *
 */
public interface CommandIcon extends Icon {
	/**
	 * Method to execute the command associated with this icon directly.
	 */
	public void executeCommand();
	/**
	 * Method to create the icon for the player, this handles the display name and lore..
	 */
	public void createIcon();
}
