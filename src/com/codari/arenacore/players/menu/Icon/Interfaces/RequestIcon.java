package com.codari.arenacore.players.menu.Icon.Interfaces;

/**
 * Interface for a request icon, an icon which prepares the chat for information entry. 
 * @author Diage, Soren025
 *
 */
public interface RequestIcon extends Icon {
	/**
	 * Sets up the chat, displays any relevant messages and enters player
	 * 	into a mode to type a response into the chat box.
	 */
	public void prepareChat();
}
