package com.codari.arenacore.develop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.codari.api5.Codari;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.players.combatants.Combatant;

public class ArenaDevelopmentKitListener implements Listener {
	//-----Fields-----//
	//---Inventory Slots---//
	private final int ITEM_SPAWNER_SLOT = ArenaDevelopmentKit.INVENTORY_STARTING_PLACEMENT_SLOT;
	private final int DIAMOND_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 2;
	private final int EMERALD_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 3;
	private final int GOLD_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 4;
	private final int IRON_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 5;
	private final int EXPLOSION_TRAP = ITEM_SPAWNER_SLOT + 6;
	private final int FIRE_TRAP = ITEM_SPAWNER_SLOT + 7;
	private final int POISON_SNARE_TRAP = ITEM_SPAWNER_SLOT + 8;
	private final int GATE = ITEM_SPAWNER_SLOT + 9;
	
	@EventHandler()
	public void onPlayerRightClickTeam(InventoryClickEvent e) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant((Player)e.getWhoClicked());
		if(Codari.INSTANCE.getArenaManager().getTeam(combatant) == null) {
			if(e.isRightClick()) {
				int clickedSlot = e.getSlot();
				Location blockDown = e.getWhoClicked().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
				switch(clickedSlot) {
				case(ITEM_SPAWNER_SLOT):
					//Set Item Spawner
					ArenaObject itemSpawner = Codari.INSTANCE.getArenaManager().createObjecto("Item_Spawner", blockDown);
					break;
				case(DIAMOND_OBJECTIVE_POINT):
					ArenaObject diamondObjectivePoint = Codari.INSTANCE.getArenaManager().createObjecto("Diamond_Objective_Point", blockDown);
					break;
				case(EMERALD_OBJECTIVE_POINT):
					ArenaObject emeraldObjectivePoint = Codari.INSTANCE.getArenaManager().createObjecto("Emerald_Objective_Point", blockDown);
					break;
				case(GOLD_OBJECTIVE_POINT):
					ArenaObject goldObjectivePoint = Codari.INSTANCE.getArenaManager().createObjecto("Gold_Objective_Point", blockDown);
					break;
				case(IRON_OBJECTIVE_POINT):
					ArenaObject ironObjectivePoint = Codari.INSTANCE.getArenaManager().createObjecto("Iron_Objective_Point", blockDown);
					break;
				case(EXPLOSION_TRAP):
					ArenaObject explosionTrap = Codari.INSTANCE.getArenaManager().createObjecto("Explosion_Trap", blockDown);
					break;
				case(FIRE_TRAP):
					ArenaObject fireTrap = Codari.INSTANCE.getArenaManager().createObjecto("Fire_Trap", blockDown);
					break;
				case(POISON_SNARE_TRAP):
					ArenaObject poisonSnareTrap = Codari.INSTANCE.getArenaManager().createObjecto("Poison_Snare_Trap", blockDown);
					break;
				case(GATE):
					ArenaObject gate = Codari.INSTANCE.getArenaManager().createObjecto("Gate", blockDown);
					break;
				}
			}	
		}
	}
	
	private final static class playerChatty implements Listener {
		//-----Fields-----//
		private Player player;
		private boolean boleano;
		
		public playerChatty(Player player) {
			this.player = player;
		}
		
		@EventHandler
		public void chatty(AsyncPlayerChatEvent e) {
			if(e.getPlayer().equals(this.player) && boleano) {
				//TODO - Do thread-safe calculations
				Bukkit.getScheduler().runTask(Codari.INSTANCE, new Runnable() {

					@Override
					public void run() {
						//DO STUFF
						
					}
					
				});
			}
		}
		
		@EventHandler
		public void playerQuit(PlayerQuitEvent e) {
			if(e.getPlayer().equals(this.player)) {
				HandlerList.unregisterAll(this);
			}
		}
	}
}
