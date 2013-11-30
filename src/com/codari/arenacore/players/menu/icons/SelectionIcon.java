package com.codari.arenacore.players.menu.icons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arenacore.players.menu.events.IconSelectionEvent;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.icons.structure.IconType;
import com.codari.arenacore.players.menu.icons.structure.SelectionEnchantment;

public class SelectionIcon extends Icon {
	private ItemMeta itemMeta;
	private boolean isSelected;
	
	public SelectionIcon(Material material, Player player, String displayName) {
		super(material, player, IconType.SELECTION);
		this.itemMeta = super.getItemMeta();
		
		this.itemMeta.setDisplayName(displayName);
		super.setItemMeta(this.itemMeta);
	}
	
	public void select() {
		this.isSelected = true;
		this.itemMeta.addEnchant(SelectionEnchantment.INSTANCE, 1, true);
		super.setItemMeta(this.itemMeta);
		Bukkit.getPluginManager().callEvent(new IconSelectionEvent(this));
	}
	
	public void unSelect() {
		this.isSelected = false;
		this.itemMeta.removeEnchant(SelectionEnchantment.INSTANCE);
		super.setItemMeta(this.itemMeta);
		Bukkit.getPluginManager().callEvent(new IconSelectionEvent(this));
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	@Override
	public boolean setItemMeta(ItemMeta itemMeta) {
		if(super.setItemMeta(itemMeta)) {
			this.itemMeta = itemMeta;
			return true;
		}
		return false;
	}
	
	@Override
	public ItemMeta getItemMeta() {
		return this.itemMeta = super.getItemMeta();
	}
}
