package com.codari.arenacore.players.builders.kit;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import com.codari.arena.rules.ArenaRoleDeclaration;
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
		this.kitBuilders = new LinkedHashMap<>();
		this.kits = new LinkedHashMap<>();
		this.savedHotbar = new ItemStack[9];
	}

	public void setSelectedKitBuilder(String name) {
		this.selectedBuilder = this.kitBuilders.get(name);
	}

	public KitBuilder getSelectedKitBuilder() {
		return this.selectedBuilder;
	}

	public void createKitBuilder(Combatant combatant, String name) {
		if(!this.kitBuilders.containsKey(name)) {
			KitBuilder kitBuilder = new KitBuilder(name);
			KitBuilderListener.currentKitBuilders.put(combatant.getPlayerReference().getName(), kitBuilder);
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "You can't create a Kit Builder with the same name as a previous one.");
		}
	}

	//---For Menu---//
	public void submitKitBuilder(KitBuilder kitBuilder) {
		String kitBuilderName = kitBuilder.getName();
		kitBuilder.addRoleDeclaration(new ArenaRoleDeclaration());	//FIXME - We will be changing this later.
		this.kitBuilders.put(kitBuilderName, kitBuilder);
		this.selectedBuilder = kitBuilder;		
	}	

	public boolean containsKitBuilder(String kitBuilderName) {
		return this.kitBuilders.containsKey(kitBuilderName);
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

	public boolean createKit(String name) {
		if(this.selectedBuilder != null) {
			Kit newKit = this.selectedBuilder.buildKit(name);
			this.kits.put(name, newKit);
			return true;
		} 
		Bukkit.broadcastMessage(ChatColor.RED + "Kit wasn't constructed because selected Kit Builder is null!");	//TODO
		return false;
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
