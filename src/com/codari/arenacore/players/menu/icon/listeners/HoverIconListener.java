package com.codari.arenacore.players.menu.icon.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.codari.arenacore.players.menu.icon.abstracts.HoverIcon;

public class HoverIconListener implements Listener {
	
	@EventHandler
	public void inputUpdateEvent(InventoryClickEvent e) {
		if(e.getView().getItem(e.getRawSlot()) instanceof HoverIcon) {
			HoverIcon hoverIcon = (HoverIcon) e.getView().getItem(e.getRawSlot());
			if(e.getClick().isKeyboardClick()) {
				if(e.getClick().equals(ClickType.DROP)) {
					hoverIcon.enterInputDigit(0);
				} else if(e.getClick().equals(ClickType.CONTROL_DROP)){
					hoverIcon.clear();
				} else {
					hoverIcon.enterInputDigit(e.getHotbarButton() + 1);
				}
			} else if(e.getClick().isRightClick()) {
				hoverIcon.backSpace();
			}
			e.setCancelled(true);
		}
	}
}
