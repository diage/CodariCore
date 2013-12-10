package com.codari.arenacore.develop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.util.Time;
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
	}

	@EventHandler()
	public void onPlayerRightClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		CombatantCore combatant = (CombatantCore) Codari.getArenaManager().getCombatant(player);
		ArenaBuilder arenaBuilder = ((ArenaManagerCore) Codari.getArenaManager()).getArenaBuilder(combatant.getArenaBuildName());
		if(((CombatantCore)combatant).checkIfBuilding()) {
			if(e.isRightClick()) {
				int clickedSlot = e.getSlot();
				Location blockDown = e.getWhoClicked().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();

				switch(clickedSlot) {
				case(ITEM_SPAWNER_SLOT): {			
					ArenaObject itemSpawner = ((LibraryCore)Codari.getLibrary()).createObject("Item_Spawner", blockDown);				
					this.playerInput.requestChat(player, itemSpawner, arenaBuilder);
				} break;
				case(DIAMOND_OBJECTIVE_POINT): {
					ArenaObject diamondObjectivePoint = ((LibraryCore)Codari.getLibrary()).createObject("Diamond_Objective_Point", blockDown);
					this.playerInput.requestChat(player, diamondObjectivePoint, arenaBuilder);				
				} break;
				case(EMERALD_OBJECTIVE_POINT): {
					ArenaObject emeraldObjectivePoint = ((LibraryCore)Codari.getLibrary()).createObject("Emerald_Objective_Point", blockDown);
					this.playerInput.requestChat(player, emeraldObjectivePoint, arenaBuilder);				
				} break;
				case(GOLD_OBJECTIVE_POINT): {
					ArenaObject goldObjectivePoint = ((LibraryCore)Codari.getLibrary()).createObject("Gold_Objective_Point", blockDown);
					this.playerInput.requestChat(player, goldObjectivePoint, arenaBuilder);
				} break;
				case(IRON_OBJECTIVE_POINT): {
					ArenaObject ironObjectivePoint = ((LibraryCore)Codari.getLibrary()).createObject("Iron_Objective_Point", blockDown);
					this.playerInput.requestChat(player, ironObjectivePoint, arenaBuilder);
				} break;
				case(EXPLOSION_TRAP): {
					ArenaObject explosionTrap = ((LibraryCore)Codari.getLibrary()).createObject("Explosion_Trap", blockDown);
					this.playerInput.requestChat(player, explosionTrap, arenaBuilder);
				} break;
				case(FIRE_TRAP): {
					ArenaObject fireTrap = ((LibraryCore)Codari.getLibrary()).createObject("Fire_Trap", blockDown);
					this.playerInput.requestChat(player, fireTrap, arenaBuilder);
				} break;
				case(POISON_SNARE_TRAP): {
					ArenaObject poisonSnareTrap = ((LibraryCore)Codari.getLibrary()).createObject("Poison_Snare_Trap", blockDown);
					this.playerInput.requestChat(player, poisonSnareTrap, arenaBuilder);
				} break;
				case(GATE): {
					ArenaObject gate = ((LibraryCore)Codari.getLibrary()).createObject("Gate", blockDown);
					arenaBuilder.registerPersistent((ImmediatePersistentObject) gate);
				} break;
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

		public void requestChat(Player player, ArenaObject arenaObject, ArenaBuilder arenaBuilder) {
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
				Bukkit.getScheduler().runTask(CodariI.INSTANCE, new Runnable() {
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
	}
}
