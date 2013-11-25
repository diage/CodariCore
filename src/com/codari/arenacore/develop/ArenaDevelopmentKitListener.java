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

import com.codari.api5.Codari;
import com.codari.api5.util.Time;
import com.codari.arena5.Arena;
import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.DelayedPersistentObject;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.objects.spawnable.SpawnableObject;

public class ArenaDevelopmentKitListener implements Listener {
	//-----Fields-----//
	private ArenaBuilder arenaBuilder;

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

	public ArenaDevelopmentKitListener(Arena arena) {
		this.arenaBuilder = arena.getArenaBuilder();
	}

	@EventHandler()
	public void onPlayerRightClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if(e.isRightClick()) {
			int clickedSlot = e.getSlot();
			Location blockDown = e.getWhoClicked().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();

			switch(clickedSlot) {
			case(ITEM_SPAWNER_SLOT):
				ArenaObject itemSpawner = Codari.INSTANCE.getArenaManager().createObjecto("Item_Spawner", blockDown);	
			new PlayerInput(player, itemSpawner, arenaBuilder);
			break;
			case(DIAMOND_OBJECTIVE_POINT):
				ArenaObject diamondObjectivePoint = Codari.INSTANCE.getArenaManager().createObjecto("Diamond_Objective_Point", blockDown);
			new PlayerInput(player, diamondObjectivePoint, arenaBuilder);
			break;
			case(EMERALD_OBJECTIVE_POINT):
				ArenaObject emeraldObjectivePoint = Codari.INSTANCE.getArenaManager().createObjecto("Emerald_Objective_Point", blockDown);
			new PlayerInput(player, emeraldObjectivePoint, arenaBuilder);
			break;
			case(GOLD_OBJECTIVE_POINT):
				ArenaObject goldObjectivePoint = Codari.INSTANCE.getArenaManager().createObjecto("Gold_Objective_Point", blockDown);
			new PlayerInput(player, goldObjectivePoint, arenaBuilder);
			break;
			case(IRON_OBJECTIVE_POINT):
				ArenaObject ironObjectivePoint = Codari.INSTANCE.getArenaManager().createObjecto("Iron_Objective_Point", blockDown);
			new PlayerInput(player, ironObjectivePoint, arenaBuilder);
			break;
			case(EXPLOSION_TRAP):
				ArenaObject explosionTrap = Codari.INSTANCE.getArenaManager().createObjecto("Explosion_Trap", blockDown);
			new PlayerInput(player, explosionTrap, arenaBuilder);
			break;
			case(FIRE_TRAP):
				ArenaObject fireTrap = Codari.INSTANCE.getArenaManager().createObjecto("Fire_Trap", blockDown);
			new PlayerInput(player, fireTrap, arenaBuilder);
			break;
			case(POISON_SNARE_TRAP):
				ArenaObject poisonSnareTrap = Codari.INSTANCE.getArenaManager().createObjecto("Poison_Snare_Trap", blockDown);
			new PlayerInput(player, poisonSnareTrap, arenaBuilder);
			break;
			case(GATE):
				ArenaObject gate = Codari.INSTANCE.getArenaManager().createObjecto("Gate", blockDown);
			this.arenaBuilder.registerPersistent((ImmediatePersistentObject) gate);
			break;
			}
		}	

	}

	private final static class PlayerInput implements Listener {
		//-----Fields-----//
		private Player player;
		private ArenaObject arenaObject;
		private ArenaBuilder arenaBuilder;

		private boolean playerInputNeeded;
		private String objectType;

		//-----Player Input Messages-----//
		private String delayedPersistentObjectMessage = "Please type in a time for the delay and true/false for the override. For example, "
				+ "type in \"5 true\".";

		private String randomSpawnableObjectMessage = "Please type in the group of random spawnables you would like this one to be in.";

		private String fixedSpawnableObjectMessage = "Please type in a time (1-99) for which to spawn the object and an optional time for "
				+ "which to repeat the spawn. For example, type in \"10\" if you want the object to spawn at 10 minutes"
				+ "or \"10 10\" if you want the object to spawn at 10 minutes and spawn consecutively afterwards"
				+ "every 10 minutes.";

		//-----Constructor-----//
		public PlayerInput(Player player, ArenaObject arenaObject, ArenaBuilder arenaBuilder) {
			this.player = player;
			this.playerInputNeeded = true;
			this.arenaObject = arenaObject;
			this.arenaBuilder = arenaBuilder;
		}

		//-----Chat Event-----//
		@EventHandler
		public void chatty(AsyncPlayerChatEvent e) {
			if(e.getPlayer().equals(this.player) && playerInputNeeded) {
				if(arenaObject instanceof SpawnableObject) {
					if(arenaObject instanceof RandomSpawnableObject) {
						objectType = "Random Spawnable Object";
						player.sendMessage(randomSpawnableObjectMessage);
					} else if(arenaObject instanceof FixedSpawnableObject) {
						objectType = "Fixed Spawnable Object";
						player.sendMessage(fixedSpawnableObjectMessage);
					}
				} else if(arenaObject instanceof DelayedPersistentObject) {
					objectType = "Delayed Persistent Object";
					player.sendMessage(delayedPersistentObjectMessage);
				}				
				final String playerInputString = e.getMessage();
				HandlerList.unregisterAll(this);
				Bukkit.getScheduler().runTask(Codari.INSTANCE, new Runnable() {
					@Override
					public void run() {
						switch(objectType) {
						case "Delayed Persistent Object":
							String delayString = playerInputString.substring(0, 2);
							String booleanString = playerInputString.substring(3);

							//Delayed Persistent Object with the delay time and the override boolean
							Time delayTime = new Time(Long.parseLong(delayString));
							boolean override = Boolean.parseBoolean(booleanString);
							arenaBuilder.registerPersistent((DelayedPersistentObject) arenaObject, delayTime, override);
							playerInputNeeded = false;
							break;
						case "Random Spawnable Object":
							//Random Spawnable Object with the given group name
							String groupName = playerInputString;
							arenaBuilder.registerRandomSpawnable((RandomSpawnableObject) arenaObject, groupName);
							playerInputNeeded = false;
							break;
						case "Fixed Spawnable Object":
							Time spawnTime;
							Time repeatSpawnTime;
							if(playerInputString.length() < 3) {
								//Fixed Spawnable Object w/ time
								spawnTime = new Time(Long.parseLong(playerInputString));
								arenaBuilder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, spawnTime);
							}  else {
								String spawnTimeString = playerInputString.substring(0, 2);
								String spawnRepeatTimeString = playerInputString.substring(3);

								//Fixed Spawnable Object w/ time and repeat time
								spawnTime = new Time(Long.parseLong(spawnTimeString));
								repeatSpawnTime = new Time(Long.parseLong(spawnRepeatTimeString));
								arenaBuilder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, spawnTime, repeatSpawnTime);
							}
							playerInputNeeded = false;
							break;
						default: 
							player.sendMessage("Error creating object. Please try again.");
							playerInputNeeded = true;
							break;
						}

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
