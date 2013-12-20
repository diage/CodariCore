package com.codari.apicore;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.arena5.ArenaEndEvent;
import com.codari.arena5.ArenaStartEvent;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.ArenaCore;

@SuppressWarnings("deprecation")
public class CoreListener implements Listener {
	private final static int MAX_HEALTH = 100;

	private Map<String, ItemStack[]> inventories;
	private Map<String, Map<String, ItemStack[]>> inGameInventories;

	public CoreListener() {
		this.inventories = new HashMap<>();
		this.inGameInventories = new HashMap<>();
	}

	@EventHandler()
	private void playerDeath(PlayerDeathEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getEntity());
		if(combatant.inArena()) {
			e.setKeepLevel(true);
			e.setNewExp(0);
			e.setDroppedExp(0);
			e.getDrops().clear();
			this.inventories.put(e.getEntity().getName(), e.getEntity().getInventory().getContents());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void playerRevive(PlayerRespawnEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) { //TODO
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "The respawn did in fact work perfectly fine, as expected!! Duh...");
			e.getPlayer().getInventory().setContents(this.inventories.get(e.getPlayer().getName()));
			e.setRespawnLocation(((ArenaCore) combatant.getTeam().getArena()).getSpawn(combatant));
			e.getPlayer().updateInventory();
			this.inventories.remove(e.getPlayer().getName());
		}
	}

	@EventHandler() 
	private void playerLeave(PlayerQuitEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) {
			Codari.getArenaManager().getArena(combatant.getArenaName()).stop();
			combatant.leaveArena();
		}
	}

	@EventHandler()
	private void playerClickInventory(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Combatant combatant = Codari.getArenaManager().getCombatant((Player)e.getWhoClicked());
			if(combatant.inArena()) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler()
	private void playerInteract(BlockPlaceEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) {
			int heldItem;
			heldItem = e.getPlayer().getInventory().getHeldItemSlot();
			e.getPlayer().getInventory().setItem(heldItem, e.getItemInHand());
			e.getPlayer().updateInventory();
			e.setCancelled(true);
		}
	}

	@EventHandler()
	private void arenaBegin(ArenaStartEvent e) {
		Map<String, ItemStack[]> playerInventories = new HashMap<>();
		for(Entry<String, Team> teamEntry : e.getArena().getTeams().entrySet()) {
			Team team = teamEntry.getValue();
			for(Combatant combatant : team.combatants()) {
				final Player player = combatant.getPlayer();
				
				playerInventories.put(player.getName(), player.getInventory().getContents());
				player.getInventory().clear();
				player.getInventory().setItem(7, new ItemStack(Material.STICK));
				player.updateInventory();
				player.setAllowFlight(true);
				player.setFlying(false);
				player.setGameMode(GameMode.SURVIVAL);
				player.setMaxHealth(MAX_HEALTH);
				player.setHealth(MAX_HEALTH);
				player.setWalkSpeed(.3f);
				player.setExp(0);
				player.setLevel(0);
				player.setFoodLevel(1);
				player.setExhaustion(0);
				playerInventories.put(player.getName(), player.getInventory().getContents());
				player.getInventory().clear();
				BukkitRunnable runner = new BukkitRunnable() {

					@Override
					public void run() {
						player.setFoodLevel(1);
						player.setExhaustion(0);
						if(!Codari.getArenaManager().getCombatant(player).inArena()) {
							super.cancel();
						}
					}
				};

				runner.runTaskTimer(CodariI.INSTANCE, 1, 1);
			}
		}
		Bukkit.broadcastMessage(ChatColor.GREEN + "Arena has succesfully been started!");
		this.inGameInventories.put(e.getArena().getName(), playerInventories);
	}

	@EventHandler()
	private void arenaEnd(ArenaEndEvent e) {
		Map<String, ItemStack[]> playerInventories = this.inGameInventories.get(e.getArena().getName());
		for(Entry<String, Team> teamEntry : e.getArena().getTeams().entrySet()) {
			Team team = teamEntry.getValue();
			for(Combatant combatant : team.combatants()) {
				Player player = combatant.getPlayer();
				
				player.getInventory().setContents(playerInventories.get(player.getName()));
				player.updateInventory();
				player.setAllowFlight(false);
				player.resetMaxHealth();
				player.setHealth(player.getMaxHealth());
				player.setWalkSpeed(.2f);
				player.setTotalExperience(0);
				player.setFoodLevel(10);
				player.setExhaustion(10);
			}
		}
		this.inGameInventories.remove(e.getArena().getName());
	}

	@EventHandler()
	private void playerDamagePlayer(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player attacker = (Player)e.getDamager(), victim = (Player)e.getEntity();
			Combatant attackerCombatant = Codari.getArenaManager().getCombatant(attacker);
			Combatant victimCombatant = Codari.getArenaManager().getCombatant(victim);
			if(attackerCombatant.inArena() && victimCombatant.inArena()) {
				if(attackerCombatant.getTeam().equals(victimCombatant.getTeam())) {
					e.setCancelled(true);
				}
			} else {
				e.setCancelled(true);
			} 
		}	
	}
}
