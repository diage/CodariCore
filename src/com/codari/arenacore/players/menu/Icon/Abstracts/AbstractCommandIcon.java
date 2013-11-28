package com.codari.arenacore.players.menu.Icon.Abstracts;

import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.Icon.Interfaces.CommandIcon;
/**
 * Class to handle Command Icons, extend this to create a command icon.
 * @author Diage, Soren025
 *
 */
public abstract class AbstractCommandIcon extends AbstractIcon implements CommandIcon {
	
	/**
	 * Standard Constructor
	 * @param itemID ID for the item that this icon will be
	 * @param player Player for which this icon is being created.
	 */
	public AbstractCommandIcon(int itemID, Player player) {
		super(itemID, player);
	}
	
	@Override
	public final void click() {
		this.executeCommand();
	}
}
