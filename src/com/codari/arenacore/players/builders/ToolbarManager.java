package com.codari.arenacore.players.builders;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.builders.kit.KitListener;

public class ToolbarManager {
	private Combatant combatant;
	private ItemStack[] savedHotbar;
	private Kit toolbarKit;
	
	public ToolbarManager(Combatant combatant) {
		this.combatant = combatant;
		this.savedHotbar = new ItemStack[9];
	}
	
	//-----TOOL BAR STUFF-----//
	@SuppressWarnings("deprecation")
	public void enableToolBar() {
		Kit kit = KitListener.getKit(this.combatant);
		if (kit == null) {
			throw new IllegalArgumentException("No current kit exists for the player " + this.combatant.getPlayer().getName());
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
		this.combatant.setHotbarActive(true);
		this.toolbarKit = kit;
		
		BuildingStartEvent e = new BuildingStartEvent(this.combatant, kit.getName());
		Bukkit.getPluginManager().callEvent(e);
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
			this.combatant.setHotbarActive(false);
			this.toolbarKit = null;
			
			BuildingEndEvent e = new BuildingEndEvent(this.combatant);
			Bukkit.getPluginManager().callEvent(e);
		}
	}

	public boolean isToolBarEnabled() {
		return this.toolbarKit != null;
	}

	public Kit getToolbarKit() {
		return this.toolbarKit;
	}
}
