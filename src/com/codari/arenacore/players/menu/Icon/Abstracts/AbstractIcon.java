package com.codari.arenacore.players.menu.Icon.Abstracts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.codari.arenacore.players.menu.Icon.Interfaces.Icon;

/**
 * Class representing an icon. 
 * @author Diage, Soren025
 *
 */
public abstract class AbstractIcon extends ItemStack implements Icon{
	/**
	 * String to get a hold of the player's name
	 */
	protected String playerName;
	
	/**
	 * Standard constructor for Icons
	 * @param itemID The ID for the item which will represent the icon.
	 * @param player The player which the icon is being made for.
	 */
	@SuppressWarnings("deprecation")
	public AbstractIcon(int itemID, Player player) {
		super(itemID);  
		this.playerName = player.getName();
	}
}
