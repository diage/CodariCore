package com.codari.arenacore.players.menu.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.codari.arenacore.players.menu.icons.SelectionIcon;

public class SelectionIconListener implements Listener {
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getItem(e.getRawSlot()) instanceof SelectionIcon) {
			SelectionIcon selectionIcon = (SelectionIcon) e.getView().getItem(e.getRawSlot());
			if(e.getClick().equals(ClickType.RIGHT) && selectionIcon.isSelected()) {
				selectionIcon.unSelect();
			} else if(e.getClick().equals(ClickType.LEFT) && !selectionIcon.isSelected()) {
				selectionIcon.select();
			}
			e.setCancelled(true);
		}
	}
}
