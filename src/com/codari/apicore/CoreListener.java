package com.codari.apicore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;

public class CoreListener implements Listener {
	private Map<String, ItemStack[]> inventories;

	public CoreListener() {
		this.inventories = new HashMap<>();
	}

	@EventHandler()
	public void playerDeath(PlayerDeathEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getEntity());
		if(combatant.inArena()) {
			e.setKeepLevel(true);
			e.setNewExp(0);
			e.setDroppedExp(0);
			e.getDrops().clear();
			this.inventories.put(e.getEntity().getName(), e.getEntity().getInventory().getContents());
		}
	}

	@EventHandler() 
	public void playerRevive(PlayerRespawnEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) {
			e.getPlayer().getInventory().setContents(this.inventories.get(e.getPlayer().getName()));
			e.setRespawnLocation(combatant.getTeam().getTeamMates(combatant).get(0).getPlayer().getLocation());
			this.inventories.remove(e.getPlayer().getName());
		}
	}

	@EventHandler() 
	public void playerLeave(PlayerQuitEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) {
			Codari.getArenaManager().getArena(combatant.getArenaName()).stop();
			combatant.leaveArena();
		}
	}

	@EventHandler()
	public void playerClickInventory(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Combatant combatant = Codari.getArenaManager().getCombatant((Player)e.getWhoClicked());
			if(combatant.inArena()) {
				e.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler()
	public void playerInteract(BlockPlaceEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) {
			int heldItem;
			heldItem = e.getPlayer().getInventory().getHeldItemSlot();
			e.getPlayer().getInventory().setItem(heldItem, e.getItemInHand());
			e.getPlayer().updateInventory();
			e.setCancelled(true);
		}
	}
}
