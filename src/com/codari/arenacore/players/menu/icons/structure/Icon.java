package com.codari.arenacore.players.menu.icons.structure;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;

public abstract class Icon extends ItemStack {
	private IconType iconType;
	protected String playerName;
	private Combatant combatant;
	
	public Icon(Material material, Combatant combatant, IconType iconType, String displayName) {
		super(material);  
		
		ItemMeta itemMeta = this.getItemMeta();
		itemMeta.setDisplayName(displayName);
		this.setItemMeta(itemMeta);
		
		this.combatant = combatant;
		this.playerName = combatant.getPlayer().getName();
		this.iconType = iconType;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
	
	public IconType getIconType() {
		return this.iconType;
	}
	
	public Combatant getCombatant() {
		return this.combatant;
	}
}
