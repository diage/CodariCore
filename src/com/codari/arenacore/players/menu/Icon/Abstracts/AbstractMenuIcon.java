package com.codari.arenacore.players.menu.Icon.Abstracts;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.UtilityMenuSlot;
import com.codari.arenacore.players.menu.Icon.IconType;
import com.codari.arenacore.players.menu.Icon.Interfaces.MenuIcon;

/**
 * Class which represents an icon that turns into a menu.
 * @author Diage, Soren025
 *
 */
public abstract class AbstractMenuIcon extends AbstractIcon implements MenuIcon{
	/**
	 * Map of menu slot to icon which declares which icons go into which slot
	 */
	protected Map<UtilityMenuSlot, IconType> menuIcons;
	//private Menu menu; //TODO: Remove - temp to fix an error, replace with the ability to grab a menu from a player.
	
	/**
	 * Standard constructor for menu icons.
	 * @param itemID The id for the item which represents this icon.
	 * @param player The player for which this icon is being produced.
	 */
	public AbstractMenuIcon(int itemID, Player player){
		super(itemID, player);
		this.menuIcons = new HashMap<>(10);
		this.createIcon();
		this.assignIcons();
	}
	
	@Override
	public Map<UtilityMenuSlot, IconType> getMenuIcons() {
		return this.menuIcons;
	}
	
	@Override
	public final void click() {
		Player player = Bukkit.getPlayer(playerName);
		for (UtilityMenuSlot menuSlot : UtilityMenuSlot.values()) {
			if (menuSlot != UtilityMenuSlot.NO_SLOT) {
				if(this.menuIcons.get(menuSlot).getIcon(player) != null) {
					//this.menu.setSlot(menuSlot, this.menuIcons.get(menuSlot).getIcon(player));
				}
			}
		}
	}
}
