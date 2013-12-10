package com.codari.arenacore.develop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArenaDevelopmentKit {
	//-----Fields-----//
	private final static int ITEM_SPAWNER = 0;
	private final static int OBJECTIVE_POINT = 1;
	private final static int TRAP = 2;
	private final static int GATE = 3;
	private final static int ROLE_DELEGATION = 4;
	
	public static final int INVENTORY_STARTING_PLACEMENT_SLOT = 9;
	
	private static ItemStack[][] arenaObjects;
	
	//-----Getters-----//
	/* Puts the necessary objects to develop the arena in the player's inventory. */
	public static void createArenaDevelopmentObjects(Player player) {
		arenaObjects = new ItemStack[5][];
		
		//Item Spawner
		arenaObjects[ITEM_SPAWNER] = new ItemStack[1];
		ItemStack itemSpawner = new ItemStack(Material.COAL_BLOCK);
		
		arenaObjects[ITEM_SPAWNER][0] = itemSpawner;
		
		//Objective Points
		arenaObjects[OBJECTIVE_POINT] = new ItemStack[4];
		ItemStack diamondObjectivePoint = new ItemStack(Material.DIAMOND_ORE);
		ItemStack emeraldObjectivePoint = new ItemStack(Material.EMERALD_ORE);
		ItemStack goldObjectivePoint = new ItemStack(Material.GOLD_ORE);
		ItemStack ironObjectivePoint = new ItemStack(Material.IRON_ORE);
		
		arenaObjects[OBJECTIVE_POINT][0] = diamondObjectivePoint;
		arenaObjects[OBJECTIVE_POINT][1] = emeraldObjectivePoint;
		arenaObjects[OBJECTIVE_POINT][2] = goldObjectivePoint;
		arenaObjects[OBJECTIVE_POINT][3] = ironObjectivePoint;
		
		//Traps
		arenaObjects[TRAP] = new ItemStack[3];
		ItemStack explosionTrap = new ItemStack(Material.PISTON_EXTENSION);
		ItemStack fireTrap = new ItemStack(Material.PISTON_BASE);
		ItemStack poisonSnareTrap = new ItemStack(Material.PISTON_MOVING_PIECE);
		
		arenaObjects[TRAP][0] = explosionTrap;
		arenaObjects[TRAP][1] = fireTrap;
		arenaObjects[TRAP][2] = poisonSnareTrap;
		
		//Gate
		arenaObjects[GATE] = new ItemStack[1];
		ItemStack gate = new ItemStack(Material.GOLD_SPADE);
		
		arenaObjects[GATE][0] = gate;		
		
		//Role Delegation Objects
		arenaObjects[ROLE_DELEGATION] = new ItemStack[3];
		ItemStack meleeRoleDelegation = new ItemStack(Material.GOLD_BLOCK);
		ItemStack rangedRoleDelegation = new ItemStack(Material.EMERALD_BLOCK);
		ItemStack randomRoleDelegation = new ItemStack(Material.DIAMOND_BLOCK);
		
		arenaObjects[ROLE_DELEGATION][0] = meleeRoleDelegation;
		arenaObjects[ROLE_DELEGATION][1] = rangedRoleDelegation;
		arenaObjects[ROLE_DELEGATION][2] = randomRoleDelegation;
		
		
		//Setting Display Names for all the objects
		setDisplayName(arenaObjects);
		
		//Putting Items in Player's Inventory
		int counter = INVENTORY_STARTING_PLACEMENT_SLOT;
		
		for(int i = 0; i < arenaObjects.length; i++) {
			for(int j = 0; j < arenaObjects[i].length; j++) {
				player.getInventory().setItem(counter++, arenaObjects[i][j]);
			}
		}	
	}
	
	private static void setDisplayName(ItemStack[][] arenaObjects) {
		for(int i = 0; i < arenaObjects.length; i ++) {
			for(int j = 0; j < arenaObjects[i].length; j++) {
				switch(i) {
				case 0: 
					ItemMeta itemSpawnerMeta = arenaObjects[i][j].getItemMeta();
					itemSpawnerMeta.setDisplayName("Place an item spawner.");
					arenaObjects[i][j].setItemMeta(itemSpawnerMeta);
					break;
				case 1:
					ItemMeta objectivePointMeta = arenaObjects[i][j].getItemMeta();
					objectivePointMeta.setDisplayName("Place an objective point.");
					arenaObjects[i][j].setItemMeta(objectivePointMeta);
					break;
				case 2:
					ItemMeta trapMeta = arenaObjects[i][j].getItemMeta();
					switch(j) {
					case 0: 
						trapMeta.setDisplayName("Place an explosion trap.");
					case 1: 
						trapMeta.setDisplayName("Place a fire trap.");
					case 2: 
						trapMeta.setDisplayName("Place a poison snare trap.");	
					}
					
					arenaObjects[i][j].setItemMeta(trapMeta);
					break;
				case 3:
					ItemMeta gateMeta = arenaObjects[i][j].getItemMeta();
					gateMeta.setDisplayName("Place a redstone used to activate a gate.");
					arenaObjects[i][j].setItemMeta(gateMeta);
					break;
				case 4:
					ItemMeta roleDelegationMeta = arenaObjects[i][j].getItemMeta();
					switch(j) {
					case 0: 
						roleDelegationMeta.setDisplayName("Place a melee role delegation object.");
					case 1: 
						roleDelegationMeta.setDisplayName("Place a ranged role delegation object.");
					case 2: 
						roleDelegationMeta.setDisplayName("Place a random role delegation object.");
					}
					arenaObjects[i][j].setItemMeta(roleDelegationMeta);
				default:
					return;
				}
			}
		}
	}

}
