package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;

public class KitManager {
	private Combatant combatant;
	private KitBuilder selectedBuilder;
	private Map<String, KitBuilder> kitBuilders;
	private Map<String, Kit> kits;
	private ItemStack[] savedHotbar;
	private boolean isToolbarEnabled;
	
	public KitManager(Combatant combatant) {
		this.combatant = combatant;
		this.kitBuilders = new HashMap<>();
		this.kits = new HashMap<>();
		this.savedHotbar = new ItemStack[9];
	}
	
	public void setSelectedKitBuilder(String name) {
		this.selectedBuilder = this.kitBuilders.get(name);
		KitBuilderListener.changeKitBuilder(combatant, this.selectedBuilder);
	}
	
	public void createKitBuilder(String name) {
		this.selectedBuilder = new KitBuilder(name);
		this.kitBuilders.put(name, this.selectedBuilder);
		KitBuilderListener.changeKitBuilder(combatant, this.selectedBuilder);
	}
	
	public Kit selectKit(String name) {
		return this.kits.get(name);
	}
	
	public Kit createKit(String name) {
		Kit newKit = this.selectedBuilder.buildKit(name);
		this.kits.put(name, newKit);
		return newKit;
	}
	
	public boolean containsKit(String kitName) {
		return this.kitBuilders.containsKey(kitName);
	}
	
	public Map<String, Kit> getKits() {
		return new HashMap<>(this.kits);
	}
	
	//-----TOOL BAR STUFF-----//
	public void enableToolBar(String kitName) {
		ItemStack[] inventoryContents = this.combatant.getPlayer().getInventory().getContents();
		String[] tools = this.kits.get(kitName).getTools();
		for (int i = 0; i < this.savedHotbar.length; i++) {
			if (!this.isToolBarEnabled()) {
				this.savedHotbar[i] = inventoryContents[i];
			}
			if (i < tools.length) {
				ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
				meta.setDisplayName(tools[i]);
				ItemStack item = new ItemStack(Material.STICK);
				item.setItemMeta(meta);
				inventoryContents[i] = item;
			} else {
				inventoryContents[i] = null;
			}
		}
		this.combatant.getPlayer().getInventory().setContents(inventoryContents);
		this.isToolbarEnabled = true;
	}
	
	public void disableToolBar() {
		if (this.isToolbarEnabled) {
			ItemStack[] inventoryContents = this.combatant.getPlayer().getInventory().getContents();
			for (int i = 0; i < this.savedHotbar.length; i++) {
				inventoryContents[i] = this.savedHotbar[i];
			}
			this.combatant.getPlayer().getInventory().setContents(inventoryContents);
		}
	}
	
	public boolean isToolBarEnabled() {
		return this.isToolbarEnabled;
	}
}
