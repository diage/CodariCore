package com.codari.apicore.item.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.codari.api5.events.itemevents.CodariItemHitEvent;
import com.codari.arena5.item.CodariItem;

public class CombatantHitEventListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	private void combatantHitEvent(CodariItemHitEvent e) {
		CodariItem item = e.getItem();
		item.attack(e.getTarget());
	}
	
}
