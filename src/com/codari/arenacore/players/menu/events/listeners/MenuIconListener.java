package com.codari.arenacore.players.menu.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.codari.api5.Codari;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.MenuManager;
import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.icons.MenuIcon;

public class MenuIconListener implements Listener {
	@EventHandler
	public void menuIconClick(InventoryClickEvent e) {
		if(e.getView().getItem(e.getRawSlot()) instanceof MenuIcon) {
			MenuIcon menuIcon = (MenuIcon) e.getView().getItem(e.getRawSlot());
			if(e.getClick().isRightClick() || e.getClick().isLeftClick()) {
				menuIcon.click();
			} 
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void menuDispatch(IconMenuClickEvent e) {
		MenuManager menuManager = ((CombatantCore)Codari.getArenaManager().getCombatant(e.getIcon().getPlayerName())).getMenuManager();
		if(!(e.getFunctionMenu() == null)) {
			menuManager.setMenu(e.getFunctionMenu());
		}
		if(!(e.getUtilityMenu() == null)) {
			menuManager.setMenu(e.getUtilityMenu());
		}
	}
}
