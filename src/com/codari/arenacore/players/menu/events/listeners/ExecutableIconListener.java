package com.codari.arenacore.players.menu.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class ExecutableIconListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getItem(e.getRawSlot()) instanceof ExecutableIcon) {
			ExecutableIcon executableIcon = (ExecutableIcon) e.getView().getItem(e.getRawSlot());
			if(e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.LEFT)) {
				executableIcon.click();
			} 
			e.setCancelled(true);
		}
	}
}
