package com.codari.arenacore.players.menu.icons.structure;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Icon extends ItemStack {
	private IconType iconType;
	protected String playerName;
	
	public Icon(Material material, Player player, IconType iconType, String displayName) {
		super(material);  
		
		ItemMeta itemMeta = this.getItemMeta();
		itemMeta.setDisplayName(displayName);
		this.setItemMeta(itemMeta);
		
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
