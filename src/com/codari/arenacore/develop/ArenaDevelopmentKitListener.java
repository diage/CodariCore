package com.codari.arenacore.develop;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.util.Time;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.DelayedPersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.objects.spawnable.SpawnableObject;
//import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.combatants.CombatantCore;

public class ArenaDevelopmentKitListener implements Listener {
	//-----Fields-----//
	private PlayerInput playerInput;

	//---Inventory Slots---//
/*	private final int ITEM_SPAWNER_SLOT = ArenaDevelopmentKit.INVENTORY_STARTING_PLACEMENT_SLOT;
	private final int DIAMOND_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 1;
	private final int EMERALD_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 2;
	private final int GOLD_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 3;
	private final int IRON_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 4;
	private final int EXPLOSION_TRAP = ITEM_SPAWNER_SLOT + 5;
	private final int FIRE_TRAP = ITEM_SPAWNER_SLOT + 6;
	private final int POISON_SNARE_TRAP = ITEM_SPAWNER_SLOT + 7;
	private final int GATE = ITEM_SPAWNER_SLOT + 8;
	private final int SPAWNNNN = ITEM_SPAWNER_SLOT + 9;
*/

	public ArenaDevelopmentKitListener() {	
		this.playerInput = new PlayerInput();
		Bukkit.getPluginManager().registerEvents(this.playerInput, CodariI.INSTANCE);
	}

	/*
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
				case(GATE): {
					ArenaObject gate = ((LibraryCore)Codari.getLibrary()).createObject("Gate", player);
					this.requestChat(player, gate, this.playerInput);
				} break;
				case(SPAWNNNN): {
					this.requestChat(player, null, this.playerInput);
				}
				}
				e.setCancelled(true);
			}	
		}
		
	}
	*/
	@SuppressWarnings("unused")
	private void requestChat(Player player, ArenaObject arenaObject, PlayerInput playerInput) {	
		String objectType;
		
		if(arenaObject instanceof SpawnableObject) {
			if(arenaObject instanceof RandomSpawnableObject) {
				objectType = "Random Spawnable Object";
				playerInput.requestChat(player, objectType, arenaObject);
			} else if(arenaObject instanceof FixedSpawnableObject) {
				objectType = "Fixed Spawnable Object";
				playerInput.requestChat(player, objectType, arenaObject);
			}
		} else if(arenaObject instanceof DelayedPersistentObject) {
			objectType = "Delayed Persistent Object";
			playerInput.requestChat(player, objectType, arenaObject);
		} else if(arenaObject == null) { 
			objectType = "Spawner";
			player.sendMessage(ChatColor.GRAY + "You have placed a spawner!");
			playerInput.requestChat(player, objectType, arenaObject);
		}		
	}

	private final static class PlayerInput implements Listener {
		private Map<String, PlayerBuildObject> playerBuildObjects = Collections.synchronizedMap(new HashMap<String, PlayerBuildObject>());
		
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
							String[] args = playerInputString.replaceAll(" ", "").split(":");
							//Create new Random Spawnable Group
							if(args.length == 3) {
								String groupName = args[0];
								if(!arenaBuilder.checkForRandomSpawnableGroup(groupName)) {
									try {
										Integer.parseInt(args[1]);
										Integer.parseInt(args[2]);
									} catch(NumberFormatException e) {
										player.sendMessage(ChatColor.RED + "Invalid parameters - please try again.");
										return;
									}	
									int delayTimeRS = Integer.parseInt(args[1]);
									int repeatTimeRS = Integer.parseInt(args[2]);
									arenaBuilder.createRandomTimelineGroup(groupName, new Time(0, delayTimeRS), new Time(0, repeatTimeRS));
									player.sendMessage(ChatColor.GREEN + "Created Random Spawnable Group named " + groupName + " Delay Time: " + delayTimeRS + " Repeat Time " + repeatTimeRS);
									arenaBuilder.registerRandomSpawnable((RandomSpawnableObject) arenaObject, groupName);
									player.sendMessage(ChatColor.GREEN + arenaObject.getClass().getSimpleName() + " has been registered inside " + groupName);
								} else {
									player.sendMessage(ChatColor.RED + "Did not work - There is already a random spawnable group with the name " + groupName);
									return;
								}
								//Add RandomSpawnableObject to an already existing random spawnable group
							} else if(arenaBuilder.checkForRandomSpawnableGroup(args[0])) {
								arenaBuilder.registerRandomSpawnable((RandomSpawnableObject) arenaObject, args[0]);
								player.sendMessage(ChatColor.GREEN + arenaObject.getClass().getSimpleName() + " has been registered inside " + args[0]);
							} else {
								player.sendMessage(ChatColor.RED + "Invalid - please try again.");
								return;
							}
							break;
						case "Fixed Spawnable Object":
							int delayTimeFS, repeatTimeFS;
							String[] fixedSpawnableObjectArgs = playerInputString.replaceAll(" ","").split(":");
							try {
								Integer.parseInt(fixedSpawnableObjectArgs[0]);
							} catch(NumberFormatException e) {
								player.sendMessage(ChatColor.RED + "Invalid parameters - please try again.");
								return;
							}
							delayTimeFS = Integer.parseInt(fixedSpawnableObjectArgs[0]);
							if(fixedSpawnableObjectArgs.length == 1) {
								//Fixed Spawnable Object w/ delay time
								arenaBuilder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, new Time(0, delayTimeFS));
								player.sendMessage(ChatColor.GREEN + arenaObject.getClass().getSimpleName() + " has been registered");
							} else if(fixedSpawnableObjectArgs.length == 2) {
								try {
									Integer.parseInt(fixedSpawnableObjectArgs[1]);
								} catch(NumberFormatException e) {
									player.sendMessage(ChatColor.RED + "Invalid parameters - please try again.");
									return;
								}
								repeatTimeFS = Integer.parseInt(fixedSpawnableObjectArgs[1]);
								//Fixed Spawnable Object w/ delay time and respawn time
								arenaBuilder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, new Time(0, delayTimeFS), new Time(0, repeatTimeFS));
								player.sendMessage(ChatColor.GREEN + arenaObject.getClass().getSimpleName() + " has been registered");
							} else {
								player.sendMessage(ChatColor.RED + "Invalid - please try again.");
								return;
							}
							break;
						case "Spawner":
							this.arenaBuilder.addSpawnLocation(player.getLocation());
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
