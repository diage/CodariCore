package com.codari.arenacore.players.menu.icon.abstracts;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.codari.arenacore.players.menu.icon.IconType;

public abstract class Icon extends ItemStack {
	private IconType iconType;
	protected String playerName;
	
	public Icon(Material material, Player player, IconType iconType) {
		super(material);  
		this.playerName = player.getName();
		this.iconType = iconType;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public IconType getIconType() {
		return this.iconType;
	}
}
