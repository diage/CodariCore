package com.codari.apicore.item.asset;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.ItemAsset;
import com.codari.arena5.players.combatants.Combatant;

public abstract class StatusAsset implements ItemAsset {
	private String damageType;
	
	public StatusAsset(CodariItem item, String assetID) {
		this.damageType = assetID;
	}
	
	@Override
	public String serializeToString() {
		return "" + this.damageType;
	}

	@Override
	public List<String> getLore() {
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Status: " + this.damageType);
		return lore;
	}
	
	public String getDamageType() {
		return this.damageType;
	}

	public abstract String getPrefixName(); 
	
	public abstract String getSuffixName();
	
	public void applyOnHitEffect(Combatant defendingCombatant) {}
	
}
