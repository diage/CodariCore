package com.codari.apicore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameMode;
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
import com.codari.arena5.ArenaEndEvent;
import com.codari.arena5.ArenaStartEvent;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;

@SuppressWarnings("deprecation")
public class CoreListener implements Listener {
	private final static int MAX_HEALTH = 100;
	
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
	
	@EventHandler()
	public void arenaBegin(ArenaStartEvent e) {
		for(Team team : e.getArena().getTeams().values()) {
			for(Combatant combatant : team.combatants()) {
				Player player = combatant.getPlayer();
				player.setAllowFlight(true);
				player.setFlying(false);
				player.setGameMode(GameMode.SURVIVAL);
				player.setMaxHealth(MAX_HEALTH);
				player.setHealth(MAX_HEALTH);
				player.setWalkSpeed(.3f);
				player.setTotalExperience(0);
				player.setFoodLevel(1);
				//TODO create a runnable for food level
			}
		}
	}
	
	@EventHandler()
	public void arenaEnd(ArenaEndEvent e) {
		for(Team team : e.getArena().getTeams().values()) {
			for(Combatant combatant : team.combatants()) {
				Player player = combatant.getPlayer();
				player.setAllowFlight(false);
				player.resetMaxHealth();
				player.setHealth(player.getMaxHealth());
				player.setWalkSpeed(.2f);
				player.setTotalExperience(0);
				player.setFoodLevel(10);
				player.getInventory().clear();
				player.updateInventory();
			}
		}
	}
}
