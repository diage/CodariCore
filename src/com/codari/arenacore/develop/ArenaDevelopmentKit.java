package com.codari.arenacore.develop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
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
	private final static int SPAWN = 4;
	
	public static final int INVENTORY_STARTING_PLACEMENT_SLOT = 9;
	
	private static ItemStack[][] arenaObjects;
	
	//-----Getters-----//
	/* Puts the necessary objects to develop the arena in the player's inventory. */
	public static void createArenaDevelopmentObjects(Player player) {
		arenaObjects = new ItemStack[5][];
		
		//Item Spawner
		arenaObjects[ITEM_SPAWNER] = new ItemStack[1];
		arenaObjects[ITEM_SPAWNER][0] =  new ItemStack(Material.COAL_BLOCK);
		
		//Objective Points
		arenaObjects[OBJECTIVE_POINT] = new ItemStack[4];
		arenaObjects[OBJECTIVE_POINT][0] = new ItemStack(Material.DIAMOND_ORE);
		arenaObjects[OBJECTIVE_POINT][1] = new ItemStack(Material.EMERALD_ORE);
		arenaObjects[OBJECTIVE_POINT][2] = new ItemStack(Material.GOLD_ORE);
		arenaObjects[OBJECTIVE_POINT][3] = new ItemStack(Material.IRON_ORE);
		
		//Traps
		arenaObjects[TRAP] = new ItemStack[3];
		arenaObjects[TRAP][0] = new ItemStack(Material.RECORD_10);
		arenaObjects[TRAP][1] = new ItemStack(Material.RECORD_11);
		arenaObjects[TRAP][2] = new ItemStack(Material.RECORD_12);
		
		//Gate
		arenaObjects[GATE] = new ItemStack[1];
		arenaObjects[GATE][0] = new ItemStack(Material.GOLD_SPADE);	
		
		//Spawn
		arenaObjects[SPAWN] = new ItemStack[1];
		arenaObjects[SPAWN][0] = new ItemStack(Material.BAKED_POTATO);
		
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
//		String delayedPersistentObjectMessage = "Please type in a time for the delay and true/false for the override. For example, "
//				+ "type in \"5 true\".";
		
		List<String> randomSpawnableLore = new ArrayList<>();
		randomSpawnableLore.add(ChatColor.RED + "To make a new random spawnable group");
		randomSpawnableLore.add(ChatColor.RED + "Type \"(groupName):(delayTime):(repeatTime)\"");
		randomSpawnableLore.add(ChatColor.RED + "Example: \"objective:30:30\"");		
		randomSpawnableLore.add(ChatColor.RED + "To add to an existing group");
		randomSpawnableLore.add(ChatColor.RED + "Type \"(groupName)\"");
		randomSpawnableLore.add(ChatColor.RED + "Example: \"objective\"");
		
		List<String> fixedSpawnableLore = new ArrayList<>();
		fixedSpawnableLore.add(ChatColor.BLUE + "To place a fixed spawnable object");
		fixedSpawnableLore.add(ChatColor.BLUE + "Type a delay time and optional repeat time");
		fixedSpawnableLore.add(ChatColor.BLUE + "Type \"(delayTime):(repeatTime)\"");
		fixedSpawnableLore.add(ChatColor.BLUE + "Example: \"10\"");
		fixedSpawnableLore.add(ChatColor.BLUE + "Example: \"20:30\"");
		
		for(int i = 0; i < arenaObjects.length; i ++) {
			for(int j = 0; j < arenaObjects[i].length; j++) {
				switch(i) {
				case 0: 
					ItemMeta itemSpawnerMeta = arenaObjects[i][j].getItemMeta();
					itemSpawnerMeta.setDisplayName(ChatColor.GOLD + "Place an item spawner.");
					itemSpawnerMeta.setLore(randomSpawnableLore);
					arenaObjects[i][j].setItemMeta(itemSpawnerMeta);
					break;
				case 1:
					ItemMeta objectivePointMeta = arenaObjects[i][j].getItemMeta();
					objectivePointMeta.setDisplayName(ChatColor.GOLD + "Place an objective point.");
					objectivePointMeta.setLore(randomSpawnableLore);
					arenaObjects[i][j].setItemMeta(objectivePointMeta);
					break;
				case 2:
					ItemMeta trapMeta = arenaObjects[i][j].getItemMeta();
					trapMeta.setLore(randomSpawnableLore);
					switch(j) {
					case 0: 
						trapMeta.setDisplayName(ChatColor.GOLD + "Place an explosion trap.");
						break;
					case 1: 
						trapMeta.setDisplayName(ChatColor.GOLD + "Place a fire trap.");
						break;
					case 2: 
						trapMeta.setDisplayName(ChatColor.GOLD + "Place a poison snare trap.");	
						break;
					}
					
					arenaObjects[i][j].setItemMeta(trapMeta);
					break;
				case 3:
					ItemMeta gateMeta = arenaObjects[i][j].getItemMeta();
					gateMeta.setDisplayName(ChatColor.GOLD + "Place a redstone used to activate a gate.");
					gateMeta.setLore(fixedSpawnableLore);
					arenaObjects[i][j].setItemMeta(gateMeta);
					break;
				case 4:
					ItemMeta playerSpawnMeta = arenaObjects[i][j].getItemMeta();
					List<String> lore = new ArrayList<>();
					playerSpawnMeta.setDisplayName(ChatColor.GOLD + "Player Spawn Location");
					lore.add(ChatColor.RED + "BAKED POTATOE!!!");
					playerSpawnMeta.setLore(lore);
					arenaObjects[i][j].setItemMeta(playerSpawnMeta);
					break;
				default:
					return;
				}
			}
		}
	}

}
