package com.codari.apicore.item.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.events.itemevents.CodariItemHitEvent;
import com.codari.apicore.CodariCore;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.players.combatants.Combatant;

public class PlayerHitEventListener implements Listener {

	@EventHandler()
	private void playerHitEvent(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player damager = (Player) e.getDamager();
			Player target = (Player) e.getEntity();
			Combatant damagerCombatant = Codari.getArenaManager().getCombatant(damager);
			Combatant targetCombatant = Codari.getArenaManager().getCombatant(target);
			if(damager.getItemInHand() != null) {
				CodariItem item = ((CodariCore) CodariI.INSTANCE).getItemDataManager().getItem(damagerCombatant, damager.getItemInHand());
				e.setCancelled(true);
				CodariItemHitEvent playerHitEvent = new CodariItemHitEvent(damagerCombatant, targetCombatant, item);
				Bukkit.getPluginManager().callEvent(playerHitEvent);
			}
		} else if(e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
			Projectile projectile = (Projectile) e.getDamager();
			if(projectile.getShooter() instanceof Player) {
				Player damager = (Player) projectile.getShooter();
				Player target = (Player) e.getEntity();
				Combatant damagerCombatant = Codari.getArenaManager().getCombatant(damager);
				Combatant targetCombatant = Codari.getArenaManager().getCombatant(target);
				if(damager.getItemInHand() != null) {
					CodariItem item = ((CodariCore) CodariI.INSTANCE).getItemDataManager().getItem(damagerCombatant, damager.getItemInHand());
					e.setCancelled(true);
					CodariItemHitEvent playerHitEvent = new CodariItemHitEvent(damagerCombatant, targetCombatant, item);
					Bukkit.getPluginManager().callEvent(playerHitEvent);
				}
			}
		}
	}

}
