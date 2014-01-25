package com.codari.arenacore.players.menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.codari.api5.Codari;
import com.codari.arenacore.players.combatants.CombatantCore;

public class MenuListener implements Listener {

	@EventHandler() 
	public void openMenuInventoryClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player && e.getInventory().getType() == InventoryType.CRAFTING) {
			Player player = ((Player)e.getWhoClicked());
			CombatantCore combatant = (CombatantCore)Codari.getArenaManager().getCombatant(player.getName());
			if(!combatant.inArena()) {
				if(e.getSlot() == 8) {
					if(!combatant.getMenuManager().isMenuOpen()) {
						combatant.getMenuManager().enterMenu();
						//FIXME - open inventory when we switch over to the other inventory type
						//combatant.getPlayer().openInventory();
						player.sendMessage(ChatColor.BLUE + "Opening menu!");	
					} else {
						combatant.getMenuManager().exitMenu();
						combatant.getPlayer().closeInventory();
						player.sendMessage(ChatColor.BLUE + "Closing menu!");	
					}
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler()
	public void openMenuInteract(PlayerInteractEvent e) {
		if(e.getPlayer().getItemInHand().hasItemMeta() && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
			if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Main Menu")) {
				Player player = e.getPlayer();
				CombatantCore combatant = (CombatantCore)Codari.getArenaManager().getCombatant(player.getName());
				if(!combatant.inArena()) {
					if(!combatant.getMenuManager().isMenuOpen()) {
						combatant.getMenuManager().enterMenu();
						//FIXME - open inventory when we switch over to the other inventory type
						player.sendMessage(ChatColor.BLUE + "Opening menu - Open your inventory!");	
					} else {
						combatant.getMenuManager().exitMenu();
						player.sendMessage(ChatColor.BLUE + "Closing menu!");	
					}
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void dropMenu(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Main Menu")) {
			e.setCancelled(true);
		}
	}

	/*	/FIXME - Removing this based on feedback. Will leave the code here until we make a final decision.
	@EventHandler()
	public void closeMenu(InventoryCloseEvent e) {
		if(e.getPlayer() instanceof Player && e.getInventory().getType() == InventoryType.CRAFTING) {
			Player player = (Player)e.getPlayer();
			CombatantCore combatant = (CombatantCore)Codari.getArenaManager().getCombatant(player.getName());
			if(!combatant.inArena()) {
				if(combatant.getMenuManager().isMenuOpen()) {
					combatant.getMenuManager().exitMenu();
					player.sendMessage(ChatColor.BLUE + "Closing menu!");	
				}
			}
		}
	}
	 */
}
