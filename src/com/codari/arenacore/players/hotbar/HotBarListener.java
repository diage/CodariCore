package com.codari.arenacore.players.hotbar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class HotBarListener implements Listener {
	@EventHandler(priority = EventPriority.MONITOR)
	private void hotbarSelect(PlayerItemHeldEvent e) {
		e.getPlayer().getInventory().setHeldItemSlot(7);
		e.getPlayer().sendMessage(String.valueOf(e.getNewSlot()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void hotbarDisable(PlayerInteractEvent e) {
		if (e.getPlayer().getInventory().getHeldItemSlot() != 7) {
			e.setCancelled(true);
		}
	}
}