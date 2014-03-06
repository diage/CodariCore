package com.codari.apicore.item.asset;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.api5.events.itemevents.CodariItemLevelEvent;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.IncrementalAsset;

public class ExperienceAsset implements IncrementalAsset {
	//-----Fields-----//
	public static final String DELIMITER = "~";
	
	private CodariItem item;
	private int curExp, maxExp;
	
	public ExperienceAsset(CodariItem item, int maxExp) {
		this.item = item;
		this.maxExp = maxExp;
	}
	
	public ExperienceAsset(CodariItem item, String serializedID) {
		String[] arguments = serializedID.split(DELIMITER, 1);
		if(arguments.length == 2) {
			this.curExp = Integer.parseInt(arguments[0]);
			this.maxExp = Integer.parseInt(arguments[1]);
		} else {
			throw new NoSuchElementException();
		}
		this.item = item;
	}
	
	@Override
	public String serializeToString() {
		return "" + this.curExp + DELIMITER + this.maxExp;
	}

	@Override
	public List<String> getLore() {
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Experience: " + this.curExp + " / " + this.maxExp);
		return lore;
	}
	
	@Override
	public void increment(int amount) {
		//Check experience left to level
		if(this.curExp + amount > this.maxExp) {
			Bukkit.getPluginManager().callEvent(new CodariItemLevelEvent(this.item));
			this.curExp = this.maxExp;
		} else {
			this.curExp += amount;
		}
	}
	
	@Override
	public void decrement(int amount) {
		if(this.curExp - amount < 0) {
			this.curExp = 0;
		} else {
			this.curExp -= amount;
		}
		
	}
	
	public void resetExp() {
		this.curExp = 0;
	}
	
	public int getCurrentExp() {
		return this.curExp;
	}
	
	public int getMaxExp() {
		return this.maxExp;
	}
	
}
