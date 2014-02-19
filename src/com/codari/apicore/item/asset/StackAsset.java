package com.codari.apicore.item.asset;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.ItemAsset;

public class StackAsset implements ItemAsset {
	private int stackSize;
	
	public StackAsset(CodariItem item, String serializedID) {
		this.stackSize = Integer.parseInt(serializedID);
	}

	@Override
	public String serializeToString() {
		return "" + this.stackSize;
	}

	@Override
	public List<String> getLore() {
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Amount: " + this.stackSize);
		return lore;
	}
	
	public void increment() {
		this.stackSize++;
	}
	
	public void increment(int amount) {
		this.stackSize += amount;
	}
 	
	public void decrement() {
		if(this.stackSize > 1) {
			this.stackSize--;
		}
	}
	
	public void decrement(int amount) {
		if(amount >= this.stackSize) {
			this.stackSize -= amount;
		} 
	}	
}
