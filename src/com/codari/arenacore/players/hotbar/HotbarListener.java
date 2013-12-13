package com.codari.arenacore.players.hotbar;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.hotbar.HotbarSelectEvent;
import com.codari.arena5.players.hotbar.HotbarSlot;

public class HotbarListener implements Listener {
	//-----Constants-----//
	private final static int DEFAULT_SLOT = 7;
	
	@EventHandler(priority = EventPriority.MONITOR)
	private void hotbarSelect(PlayerItemHeldEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if (combatant.isHotbarActive()) {
			e.getPlayer().getInventory().setHeldItemSlot(DEFAULT_SLOT);
			if (e.getPreviousSlot() == DEFAULT_SLOT && !combatant.isHotbarOnCooldown()) {
				int slotSelection = e.getNewSlot();
				if (HotbarSlot.isHotbarSlot(slotSelection)) {
					HotbarSlot slot = HotbarSlot.fromInventorySlot(slotSelection);
					HotbarSelectEvent event = new HotbarSelectEvent(combatant, slot);
					Bukkit.getPluginManager().callEvent(event);
					slot.setItem(combatant.getPlayer(), event.getItem());
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	private void hotbarDisable(PlayerInteractEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if (combatant.isHotbarActive() && e.getPlayer().getInventory().getHeldItemSlot() != 7) {
			e.setCancelled(true);
		}
	}
}