package com.codari.arenacore.players.menu.Icon.Abstracts;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.Icon.Interfaces.RequestIcon;
/**
 * Class which represents a request icon, a request icon prepares the chat for information entry.
 * @author Diage, Soren025
 *
 */
public abstract class AbstractRequestIcon extends AbstractIcon implements RequestIcon{
	/**
	 * Standard constructor for request icons.
	 * @param itemID The id for the item which represents the icon.
	 * @param player The player for which this icon is being created.
	 */
	public AbstractRequestIcon(int itemID, Player player) {
		super(itemID, player);
	}
	
	@Override
	public final void click() {
		Player player = Bukkit.getPlayer(this.playerName);
		player.closeInventory();
		this.prepareChat();
	}
}
