package com.codari.arenacore.players.menu.Icon.Abstracts;

import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.Icon.Interfaces.InfoIcon;

/**
 * Class to handle icons which only show information
 * @author Diage, Soren025
 *
 */
public abstract class AbstractInfoIcon extends AbstractIcon implements InfoIcon{
	/**
	 * Standard constructor for info icon
	 * @param itemID The id for the item which represents this icon.
	 * @param player The player for which the icon is being created.
	 */
	public AbstractInfoIcon(int itemID, Player player) {
		super(itemID, player);
		this.createIcon(player);
	}
	
	@Override
	public final void click() {/*Doesn't do anything when clicked.*/};
}
