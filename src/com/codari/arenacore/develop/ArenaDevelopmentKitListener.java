package com.codari.arenacore.develop;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.util.Time;
import com.codari.apicore.CodariCore;
import com.codari.arena.objects.role.delegation.MeleeRoleDelegation;
import com.codari.arena.objects.role.delegation.RandomRoleDelegation;
import com.codari.arena.objects.role.delegation.RangedRoleDelegation;
import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.DelayedPersistentObject;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.objects.spawnable.SpawnableObject;
import com.codari.arenacore.ArenaManagerCore;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.players.combatants.CombatantCore;

public class ArenaDevelopmentKitListener implements Listener {
	//-----Fields-----//
	private PlayerInput playerInput;

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
	private final int MELEE_ROLE_DELEGATION = ITEM_SPAWNER_SLOT + 10;
	private final int RANGED_ROLE_DELEGATION = ITEM_SPAWNER_SLOT + 11;
	private final int RANDOM_ROLE_DELEGATION = ITEM_SPAWNER_SLOT + 12;

	public ArenaDevelopmentKitListener() {	
		this.playerInput = new PlayerInput();
		Bukkit.getPluginManager().registerEvents(this.playerInput, CodariI.INSTANCE);
	}

	@EventHandler()
	public void onPlayerRightClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		CombatantCore combatant = (CombatantCore) Codari.getArenaManager().getCombatant(player);
		if(((CombatantCore)combatant).checkIfBuilding()) {
			if(e.isRightClick()) {
				int clickedSlot = e.getSlot();
				//Location player = e.getWhoClicked().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
				
				switch(clickedSlot) {
				case(ITEM_SPAWNER_SLOT): {			
					ArenaObject itemSpawner = ((LibraryCore)Codari.getLibrary()).createObject("Item_Spawner", player);				
					this.requestChat(player, itemSpawner, this.playerInput);
				} break;
				case(DIAMOND_OBJECTIVE_POINT): {
					ArenaObject diamondObjectivePoint = ((LibraryCore)Codari.getLibrary()).createObject("Diamond_Objective_Point", player);
					this.requestChat(player, diamondObjectivePoint, this.playerInput);				
				} break;
				case(EMERALD_OBJECTIVE_POINT): {
					ArenaObject emeraldObjectivePoint = ((LibraryCore)Codari.getLibrary()).createObject("Emerald_Objective_Point", player);
					this.requestChat(player, emeraldObjectivePoint, this.playerInput);				
				} break;
				case(GOLD_OBJECTIVE_POINT): {
					ArenaObject goldObjectivePoint = ((LibraryCore)Codari.getLibrary()).createObject("Gold_Objective_Point", player);
					this.requestChat(player, goldObjectivePoint, this.playerInput);
				} break;
				case(IRON_OBJECTIVE_POINT): {
					ArenaObject ironObjectivePoint = ((LibraryCore)Codari.getLibrary()).createObject("Iron_Objective_Point", player);
					this.requestChat(player, ironObjectivePoint, this.playerInput);
				} break;
				case(EXPLOSION_TRAP): {
					ArenaObject explosionTrap = ((LibraryCore)Codari.getLibrary()).createObject("Explosion_Trap", player);
					this.requestChat(player, explosionTrap, this.playerInput);
				} break;
				case(FIRE_TRAP): {
					ArenaObject fireTrap = ((LibraryCore)Codari.getLibrary()).createObject("Fire_Trap", player);
					this.requestChat(player, fireTrap, this.playerInput);
				} break;
				case(POISON_SNARE_TRAP): {
					ArenaObject poisonSnareTrap = ((LibraryCore)Codari.getLibrary()).createObject("Poison_Snare_Trap", player);
					this.requestChat(player, poisonSnareTrap, this.playerInput);
				} break;
//				case(GATE): {
//					ArenaObject gate = ((LibraryCore)Codari.getLibrary()).createObject("Gate", player);
//					arenaBuilder.registerPersistent((ImmediatePersistentObject) gate);
//				} break;
				case(MELEE_ROLE_DELEGATION): {
					new	MeleeRoleDelegation(player);
				} break;
				case(RANGED_ROLE_DELEGATION): {
					new	RangedRoleDelegation(player);
				} break;
				case(RANDOM_ROLE_DELEGATION): {
					new	RandomRoleDelegation(player);
				} break;			
				}
				e.setCancelled(true);
				player.sendMessage("Placed item."); //Debugging purposes
			}	
		}
		
	}
	
	private void requestChat(Player player, ArenaObject arenaObject, PlayerInput playerInput) {
		//-----Player Input Messages-----//
		String delayedPersistentObjectMessage = "Please type in a time for the delay and true/false for the override. For example, "
				+ "type in \"5 true\".";

		String randomSpawnableObjectMessage = "Please type in the group of random spawnables you would like this one to be in.";

		String fixedSpawnableObjectMessage = "Please type in a time (1-99) for which to spawn the object and an optional time for "
				+ "which to repeat the spawn. For example, type in \"10\" if you want the object to spawn at 10 minutes"
				+ "or \"10 10\" if you want the object to spawn at 10 minutes and spawn consecutively afterwards"
				+ "every 10 minutes.";		
		String objectType;
		
		if(arenaObject instanceof SpawnableObject) {
			if(arenaObject instanceof RandomSpawnableObject) {
				objectType = "Random Spawnable Object";
				player.sendMessage(randomSpawnableObjectMessage);
				playerInput.requestChat(player, objectType, arenaObject);
			} else if(arenaObject instanceof FixedSpawnableObject) {
				objectType = "Fixed Spawnable Object";
				player.sendMessage(fixedSpawnableObjectMessage);
				playerInput.requestChat(player, objectType, arenaObject);
			}
		} else if(arenaObject instanceof DelayedPersistentObject) {
			objectType = "Delayed Persistent Object";
			player.sendMessage(delayedPersistentObjectMessage);
			playerInput.requestChat(player, objectType, arenaObject);
		}	
		
	}

	private final static class PlayerInput implements Listener {
		private Map<String, PlayerBuildObject> playerBuildObjects = new HashMap<>();
		
		public void requestChat(Player player,  String objectType, ArenaObject arenaObject) {
			this.playerBuildObjects.put(player.getName(), new PlayerBuildObject(objectType, arenaObject));
		}

		//-----Chat Event-----//
		@EventHandler
		public void chatty(AsyncPlayerChatEvent e) {
			if(this.playerBuildObjects.containsKey(e.getPlayer().getName())) {
				final Player player = e.getPlayer();
				final String playerInputString = e.getMessage();
				Bukkit.getScheduler().runTask(CodariI.INSTANCE, new Runnable() {
					private String objectType = playerBuildObjects.get(player.getName()).getObjectName();
					private ArenaObject arenaObject = playerBuildObjects.get(player.getName()).getArenaObject();
					private ArenaBuilder arenaBuilder = ((ArenaManagerCore)Codari.getArenaManager()).getArenaBuilder(
							((CombatantCore)Codari.getArenaManager().getCombatant(player.getName())).getArenaBuildName());
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
							break;
						case "Random Spawnable Object":
							//Random Spawnable Object with the given group name
							String groupName = playerInputString;
							arenaBuilder.registerRandomSpawnable((RandomSpawnableObject) arenaObject, groupName);
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
							break;
						default: 
							player.sendMessage("Error creating object. Please try again.");
							break;
						}

					}	
				});
			}
			
		}
		
		private final static class PlayerBuildObject {
			String objectName;
			ArenaObject arenaObject;
			
			public PlayerBuildObject(String objectName, ArenaObject arenaObject) {
				this.objectName = objectName;
				this.arenaObject = arenaObject;
			}
					
			public String getObjectName() {
				return this.objectName;
			}
			
			public ArenaObject getArenaObject() {
				return this.arenaObject;
			}
		}
	}
}
