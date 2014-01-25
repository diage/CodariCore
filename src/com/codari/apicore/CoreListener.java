package com.codari.apicore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.arena5.arena.events.ArenaEndEvent;
import com.codari.arena5.arena.events.ArenaStartEvent;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.hotbar.HotbarOption;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.ArenaCore;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.role.RoleCore;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

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
			Player player = e.getEntity();
			
			{//TODO Uncomment this in 1.7, It is an auto respawn thingy
/*		        try {
		            Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
		            Object packet = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".PacketPlayInClientCommand").newInstance();
		            Class<?> enumClass = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EnumClientCommand");
		 
		            for(Object ob : enumClass.getEnumConstants()){
		                if(ob.toString().equals("PERFORM_RESPAWN")){
		                    packet = packet.getClass().getConstructor(enumClass).newInstance(ob);
		                }
		            }
		 
		            Object con = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
		            con.getClass().getMethod("a", packet.getClass()).invoke(con, packet);
		        }
		        catch(Throwable t){
		            t.printStackTrace();
		        }*/
			}
			
			e.setKeepLevel(true);
			e.setNewExp(0);
			e.setDroppedExp(0);
			e.getDrops().clear();
			
			for(HotbarOption option : HotbarOption.values()) {
				if(option.isInventorySlot() && !option.equals(HotbarOption.HOTBAR_1)) {
					player.getInventory().setItem(option.getInventorySlot(), null);
				}
			}
			for (PotionEffect type : player.getActivePotionEffects()) {
				player.removePotionEffect(type.getType());
			}
			player.getInventory().setItem(7, new ItemStack(Material.STICK));
			player.getInventory().setArmorContents(new ItemStack[4]);
			player.updateInventory();
			this.inventories.put(e.getEntity().getName(), e.getEntity().getInventory().getContents());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void playerRevive(PlayerRespawnEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) { 
			e.getPlayer().getInventory().setContents(this.inventories.get(e.getPlayer().getName()));
			e.setRespawnLocation(((ArenaCore) combatant.getTeam().getArena()).getSpawn(combatant));
			e.getPlayer().updateInventory();
			e.getPlayer().setWalkSpeed(0.3f);
			this.inventories.remove(e.getPlayer().getName());
		}
	}

	@EventHandler() 
	private void playerLeave(PlayerQuitEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) {
			Codari.getArenaManager().getArena(combatant.getArenaName()).stop();
			combatant.leaveArena();
			TeamBuilder.removePlayer((TeamCore)combatant.getTeam(), combatant.getPlayer());
		}
	}
	
	@EventHandler()
	private void stopSorenTryingToBeGay(PlayerGameModeChangeEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena() && !e.getNewGameMode().equals(GameMode.SURVIVAL)) {
			combatant.getPlayer().setGameMode(GameMode.SURVIVAL);
			combatant.getPlayer().setAllowFlight(true);
			e.setCancelled(true);
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
				player.getInventory().setHeldItemSlot(7);
				player.getInventory().setItem(25, new ItemStack(Material.ARROW));
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
				player.setSaturation(0);
				for (PotionEffect type : player.getActivePotionEffects()) {
					player.removePotionEffect(type.getType());
				}
				player.getInventory().setArmorContents(new ItemStack[4]);
				player.updateInventory();
				
				
				BukkitRunnable runner = new BukkitRunnable() {

					@Override
					public void run() {
						if(Codari.getArenaManager().getCombatant(player).inArena()) {
							player.setFoodLevel(1);
							player.setExhaustion(0);
							player.setSaturation(0);
						} else {
							super.cancel();
						}
					}
				};

				runner.runTaskTimer(CodariI.INSTANCE, 1, 1);
				
			}
		}
		this.inGameInventories.put(e.getArena().getName(), playerInventories);
	}

	@EventHandler()
	private void arenaEnd(ArenaEndEvent e) {
		Map<String, ItemStack[]> playerInventories = this.inGameInventories.get(e.getArena().getName());
		for(Entry<String, Team> teamEntry : e.getArena().getTeams().entrySet()) {
			Team team = teamEntry.getValue();
			for(Combatant combatant : team.combatants()) {
				combatant.setRole(new RoleCore(CombatantCore.NON_COMBATANT, null));
				Player player = combatant.getPlayer();
				player.getInventory().setContents(playerInventories.get(player.getName()));
				player.updateInventory();
				player.setAllowFlight(false);
				player.setMaxHealth(20);
				player.setHealth(player.getMaxHealth());
				player.setWalkSpeed(.2f);
				player.setTotalExperience(0);
				player.setSaturation(20f);
				player.setFoodLevel(20);
				for (PotionEffect type : player.getActivePotionEffects()) {
					player.removePotionEffect(type.getType());
				}
			}
		}
		this.inGameInventories.remove(e.getArena().getName());
	}

	@EventHandler()
	private void playerDamagePlayer(EntityDamageByEntityEvent e) {
		//Prevent Teamates from melee attacking eachother
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player attacker = (Player)e.getDamager(), victim = (Player)e.getEntity();
			Combatant attackerCombatant = Codari.getArenaManager().getCombatant(attacker);
			Combatant victimCombatant = Codari.getArenaManager().getCombatant(victim);
			if(checkIfInArena(attackerCombatant, victimCombatant)) {
				if(checkIfSameTeam(attackerCombatant, victimCombatant)) {
					e.setCancelled(true);
				}
			} else {
				e.setCancelled(true);
			} 
		//Prevent Teamates from shooting arrows at eachother
		} else if (e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
			Projectile proj = (Projectile) e.getDamager();
			if(proj instanceof Arrow) {
				Arrow arrow = (Arrow) proj;
				if(arrow.getShooter() instanceof Player) {
					Combatant shooter = Codari.getArenaManager().getCombatant(((Player)proj.getShooter()));
					Combatant victim = Codari.getArenaManager().getCombatant((Player)e.getEntity());
					if(checkIfInArena(shooter, victim)) {
						if(checkIfSameTeam(shooter, victim)) {
							e.setCancelled(true);
						}
					} else {
						e.setCancelled(true);
					}
				}
			}
		}	
	}
	
	//Prevent Teamates from throwing potions at eachother
	@EventHandler()
	private void onPotionSplash(PotionSplashEvent e) {
		if(e.getPotion().getShooter() instanceof Player) {
			Combatant shooter = Codari.getArenaManager().getCombatant(((Player)e.getPotion().getShooter()));
			for(LivingEntity entity : e.getAffectedEntities()) {
				if(entity instanceof Player) {
					Combatant victim = Codari.getArenaManager().getCombatant(((Player)entity));
					if(checkIfInArena(shooter, victim)) {
						if(!checkIfBeneficialPotion(e.getPotion().getEffects())) {
							if(checkIfSameTeam(shooter, victim)) {
								e.setIntensity(victim.getPlayer(), 0);
							}						
						} else {
							if(!checkIfSameTeam(shooter, victim)) {
								e.setIntensity(victim.getPlayer(), 0);	
							}
						}
					} 
				}
			}
		}
	}
	
	@EventHandler()
	private void preventBlockDestruction(BlockBreakEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) {
			e.setCancelled(true);
		}
	}
		
	private static boolean checkIfInArena(Combatant attacker, Combatant victim) {
		if(attacker.inArena() && victim.inArena()) {
			return true;
		}
		return false;
	}
	private static boolean checkIfSameTeam(Combatant attacker, Combatant victim) {
		if(attacker.getTeam().equals(victim.getTeam())) {
			return true;
		}
		return false;
	}
	
	private static boolean checkIfBeneficialPotion(Collection<PotionEffect> potionEffects) {
		for(PotionEffect potionEffect : potionEffects) {
			PotionType type = PotionType.getByEffect(potionEffect.getType());
			switch(type) {
			case FIRE_RESISTANCE:
				return true;
			case INSTANT_DAMAGE:
				return false;
			case INSTANT_HEAL:
				return true;
			case INVISIBILITY:
				return true;
			case NIGHT_VISION:
				return true;
			case POISON:
				return false;
			case REGEN:
				return true;
			case SLOWNESS:
				return false;
			case SPEED:
				return true;
			case STRENGTH:
				return true;
			case WATER:
				return true;
			case WEAKNESS:
				return false;
			}
		}
		return false;
	}
}
