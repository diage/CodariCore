package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.menus.menustore.function.kitbuilders.KitBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.function.kits.KitCreationBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.function.kits.KitSelection;
import com.codari.arenacore.players.menu.menus.menustore.function.kits.SpawnableGroupSelection;

public class KitManager {
	private Combatant combatant;
	private KitBuilder selectedBuilder;
	private Map<String, KitBuilder> kitBuilders;
	private Map<String, Kit> kits;
	private Map<String, KitSelection> kitSelectionMenus;
	private Map<String, KitBuilderSelection> kitBuilderSelectionMenus;
	private Map<String, KitCreationBuilderSelection> kitCreationBuilderSelectionMenus;
	private Map<String, SpawnableGroupSelection> spawnableGroupSelectionMenus;
	private ItemStack[] savedHotbar;
	private Kit toolbarKit;
	
	public KitManager(Combatant combatant) {
		this.combatant = combatant;
		this.kitBuilders = new LinkedHashMap<>();
		this.kits = new LinkedHashMap<>();
		this.kitSelectionMenus = new HashMap<>();
		this.kitBuilderSelectionMenus = new HashMap<>();
		this.kitCreationBuilderSelectionMenus = new HashMap<>();
		this.spawnableGroupSelectionMenus = new HashMap<>();
		this.savedHotbar = new ItemStack[9];
	}
	
	public void setSelectedKitBuilder(String name) {
		this.selectedBuilder = this.kitBuilders.get(name);
		KitBuilderListener.changeKitBuilder(combatant, this.selectedBuilder);
	}
	
	public KitBuilder getSelectedKitBuilder() {
		return this.selectedBuilder;
	}
	
	public void createKitBuilder(String name) {
		this.selectedBuilder = new KitBuilder(name);
		this.kitBuilders.put(name, this.selectedBuilder);
		KitBuilderListener.changeKitBuilder(combatant, this.selectedBuilder);
	}
	
	public Kit getKit(String name) {
		if(!this.kits.containsKey(name)) {
			Bukkit.broadcastMessage(ChatColor.RED + "You got an invalid kit name!!"); //TODO
		}
		return this.kits.get(name);
	}
	
	public KitBuilder getKitBuilder(String name) {
		if(!this.kitBuilders.containsKey(name)) {
			Bukkit.broadcastMessage(ChatColor.RED + "You got an invalid kit builder name!!"); //TODO
		}
		return this.kitBuilders.get(name);
	}
	
	public Kit createKit(String name) {
		Kit newKit = this.selectedBuilder.buildKit(name);
		this.kits.put(name, newKit);
		return newKit;
	}
	
	public boolean containsKit(String kitName) {
		return this.kits.containsKey(kitName);
	}
	
	public void renameKit(String oldKitName, String newKitName) {
		if(this.containsKit(oldKitName)) {
			Kit kit = this.kits.get(oldKitName);
			this.kits.remove(oldKitName);
			kit.setName(newKitName);
			this.kits.put(newKitName, kit);
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "You can't rename a kit that doesn't exist in the kit manager!"); //TODO
		}
	}
	
	public void setKitSelectionMenu(Combatant combatant, KitSelection kitSelection) {
		this.kitSelectionMenus.put(combatant.getPlayerReference().getName(), kitSelection);
	}
	
	public void addKitIcon(Combatant combatant, String kitName) {
		this.kitSelectionMenus.get(combatant.getPlayerReference().getName()).addKitIcon(combatant, kitName);
	}
	
	public void setKitBuilderSelectionMenu(Combatant combatant, KitBuilderSelection kitBuilderSelection) {
		this.kitBuilderSelectionMenus.put(combatant.getPlayerReference().getName(), kitBuilderSelection);
	}
	
	public void setKitCreationBuilderSelectionMenu(Combatant combatant, KitCreationBuilderSelection kitCreationBuilderSelection) {
		this.kitCreationBuilderSelectionMenus.put(combatant.getPlayerReference().getName(), kitCreationBuilderSelection);
	}
	
	public void addKitBuilderIcon(Combatant combatant, String kitBuilderName) {
		this.kitBuilderSelectionMenus.get(combatant.getPlayerReference().getName()).addKitBuilderIcon(combatant, kitBuilderName);
		this.kitCreationBuilderSelectionMenus.get(combatant.getPlayerReference().getName()).addKitCreationBuilderIcon(combatant, kitBuilderName);
	}
	
	public void setSpawnableGroupSelectionMenu(Combatant combatant, SpawnableGroupSelection spawnableGroupSelection) {
		this.spawnableGroupSelectionMenus.put(combatant.getPlayerReference().getName(), spawnableGroupSelection);
	}
	
	public void addSpawnableGroupIcon(Combatant combatant, String groupName) {
		this.spawnableGroupSelectionMenus.get(combatant.getPlayerReference().getName()).addSpawnableGroupIcon(combatant, groupName);
	}
	
	public Map<String, Kit> getKits() {
		return new LinkedHashMap<>(this.kits);
	}
	
	public Map<String, KitBuilder> getKitBuilders() {
		return new LinkedHashMap<>(this.kitBuilders);
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
		this.combatant.setHotbarActibe(true);
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
			this.combatant.setHotbarActibe(false);
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
