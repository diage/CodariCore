package com.codari.arenacore.players.hotbar;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import com.codari.api5.Codari;
import com.codari.api5.util.scheduler.BukkitTime;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.hotbar.HotbarSelectEvent;
import com.codari.arena5.players.hotbar.HotbarSlot;

public class HotBarListener implements Listener {
	//-----Constants-----//
	private final static int DEFAULT_SLOT = 7;
	
	@EventHandler(priority = EventPriority.MONITOR)
	private void hotbarSelect(PlayerItemHeldEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if (combatant.isHotbarActive()) {
			e.getPlayer().getInventory().setHeldItemSlot(DEFAULT_SLOT);
			if (e.getPreviousSlot() == DEFAULT_SLOT && !combatant.isHotbarOnCooldown()) {
				int slotSelection = e.getNewSlot();
				if (slotSelection >= 0 && slotSelection < 6) {
					HotbarSlot slot = HotbarSlot.values()[slotSelection];
					Bukkit.getPluginManager().callEvent(new HotbarSelectEvent(combatant, slot,
							combatant.getPlayer().getInventory().getItem(slotSelection)));
					combatant.setHotbarCooldown(BukkitTime.SECOND.tickValue(5));
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