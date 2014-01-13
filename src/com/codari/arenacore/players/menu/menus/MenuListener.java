package com.codari.arenacore.players.menu.menus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.codari.api5.Codari;
import com.codari.arenacore.players.combatants.CombatantCore;

public class MenuListener implements Listener {

	@EventHandler() 
	public void openMenuInventoryClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player player = ((Player)e.getWhoClicked());
			CombatantCore combatant = (CombatantCore)Codari.getArenaManager().getCombatant(player.getName());
			if(!combatant.inArena()) {
				if(e.getSlot() == 8) {
					if(!combatant.getMenuManager().isMenuOpen()) {
						combatant.getMenuManager().enterMenu();
						player.sendMessage(ChatColor.BLUE + "Opening menu!");	
					} else {
						combatant.getMenuManager().exitMenu();
						player.sendMessage(ChatColor.BLUE + "Closing menu!");	
					}
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler()
	public void closeMenu(InventoryCloseEvent e) {
		if(e.getPlayer() instanceof Player) {
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
}
