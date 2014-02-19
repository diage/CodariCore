package com.codari.apicore.item.asset;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.ItemAsset;

public class ExperienceAsset implements ItemAsset {
	private int curExp;
	
	public ExperienceAsset(CodariItem item, String serializedID) {
		this.curExp = Integer.parseInt(serializedID);
	}
	
	@Override
	public String serializeToString() {
		return "" + this.curExp;
	}

	@Override
	public List<String> getLore() {
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Amount: " + this.curExp);
		return lore;
	}
	
	public void increment(int amount) {
		//Check experience left to level
		this.curExp += amount;
	}
	
	
}
