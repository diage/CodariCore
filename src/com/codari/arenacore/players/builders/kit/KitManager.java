package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.codari.arena5.players.combatants.Combatant;

public class KitManager {
	private Combatant combatant;
	private KitBuilder selectedBuilder;
	private Map<String, KitBuilder> kitBuilders;
	private Map<String, Kit> kits;
	private ItemStack[] savedHotbar;
	private Kit toolbarKit;
	
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
		if(!this.kits.containsKey(name)) {
			Bukkit.broadcastMessage(ChatColor.RED + "You got an invalid string!!"); //TODO
		}
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
	
	public Map<String, KitBuilder> getKitBuilders() {
		return new HashMap<>(this.kitBuilders);
	}
	
	//-----TOOL BAR STUFF-----//
	@SuppressWarnings("deprecation")
	public void enableToolBar(String kitName) {
		Kit kit = this.kits.get(kitName);
		if (kit == null) {
			throw new IllegalArgumentException("no kit exists with the name " + kitName +
					" for the player " + this.combatant.getPlayer());
		}
		ItemStack[] inventoryContents = this.combatant.getPlayer().getInventory().getContents();
		ItemStack[] tools = kit.getTools();
		for (int i = 0; i < this.savedHotbar.length; i++) {
			if (!this.isToolBarEnabled()) {
				this.savedHotbar[i] = inventoryContents[i];
			}
			inventoryContents[i] = tools[i];
		}
		this.combatant.getPlayer().getInventory().setContents(inventoryContents);
		this.combatant.getPlayer().updateInventory();
		this.toolbarKit = kit;
	}
	
	@SuppressWarnings("deprecation")
	public void disableToolBar() {
		if (this.isToolBarEnabled()) {
			ItemStack[] inventoryContents = this.combatant.getPlayer().getInventory().getContents();
			for (int i = 0; i < this.savedHotbar.length; i++) {
				inventoryContents[i] = this.savedHotbar[i];
			}
			this.combatant.getPlayer().getInventory().setContents(inventoryContents);
			this.combatant.getPlayer().updateInventory();
			this.toolbarKit = null;
		}
	}
	
	public boolean isToolBarEnabled() {
		return this.toolbarKit != null;
	}
	
	public Kit getToolbarKit() {
		return this.toolbarKit;
	}
}
