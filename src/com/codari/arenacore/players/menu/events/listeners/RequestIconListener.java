package com.codari.arenacore.players.menu.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.codari.arenacore.players.menu.icons.RequestIcon;

public class RequestIconListener implements Listener {
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getItem(e.getRawSlot()) instanceof RequestIcon) {
			RequestIcon requestIcon = (RequestIcon) e.getView().getItem(e.getRawSlot());
			if(e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.LEFT)) {
				requestIcon.startConversation();
			} 
			e.setCancelled(true);
		}
	}
}
