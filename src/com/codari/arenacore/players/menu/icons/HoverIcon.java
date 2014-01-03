package com.codari.arenacore.players.menu.icons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.events.IconHoverUpdateEvent;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.icons.structure.IconType;

public class HoverIcon extends Icon {
	private int input;
	private ItemMeta itemMeta;
	private int inputRow;
	
	public HoverIcon(Material material, Combatant combatant, String displayName) {
		super(material, combatant, IconType.HOVER, displayName);
		this.itemMeta = super.getItemMeta();
		this.input = 0;
	}

	public void enterInputDigit(int input) {
		int initialInput = this.input;
		
		if(input > 0 && input <10) {
			this.input = (this.input * 10) + input;
		} else {
			throw new IllegalArgumentException("Number input to Hover Icon not between 0-9");
		}
		this.updateLore();
		Bukkit.getPluginManager().callEvent(new IconHoverUpdateEvent(this, this.input, initialInput));
	}
	
	public void backSpace() {
		int initialInput = this.input;
		this.input = this.input / 10;
		this.updateLore();
		Bukkit.getPluginManager().callEvent(new IconHoverUpdateEvent(this, this.input, initialInput));
	}
	
	public void clear() {
		int initialInput = this.input;
		this.input = 0;
		this.updateLore();
		Bukkit.getPluginManager().callEvent(new IconHoverUpdateEvent(this, this.input, initialInput));
	}
	
	public void setInputRow(int inputRow) {
		this.inputRow = inputRow - 1;
	}
	
	public int getInputRow() {
		return this.inputRow + 1;
	}
	
	public int getInput() {
		return this.input;
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
	
	private void updateLore() {
		List<String> newLore = itemMeta.getLore();
		if(newLore == null) {
			newLore = new ArrayList<>();
		}
		if(this.inputRow < newLore.size()) {
			newLore.set(this.inputRow, "" + this.input);
		} else {
			int newInt = this.inputRow - newLore.size();
			while(newInt > 0) {
				newLore.add("");
				newInt--;
			}
			newLore.add("" + this.input);
		}
		this.itemMeta.setLore(newLore);
		super.setItemMeta(this.itemMeta);
	}
}
