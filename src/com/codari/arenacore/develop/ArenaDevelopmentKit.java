package com.codari.arenacore.develop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.Arena;

public class ArenaDevelopmentKit {
	//-----Fields-----//
	private final static int ITEM_SPAWNER = 0;
	private final static int OBJECTIVE_POINT = 1;
	private final static int TRAP = 2;
	private final static int GATE = 3;
	
	public static final int INVENTORY_STARTING_PLACEMENT_SLOT = 9;
	
	private ItemStack[][] arenaObjects;
	private Arena arena;
	
	//-----Constructor------//
	public ArenaDevelopmentKit(Arena arena) {
		this.arena = arena;
		new ArenaDevelopmentKitListener(arena);
	}
	
	//-----Getters-----//
	public Arena getArena() {
		return this.arena;
	}
	
	/* Puts the necessary objects to develop the arena in the player's inventory. */
	public void createArenaDevelopmentObjects(Player player) {
		this.arenaObjects = new ItemStack[4][];
		
		//Item Spawner
		this.arenaObjects[ITEM_SPAWNER] = new ItemStack[1];
		ItemStack itemSpawner = new ItemStack(Material.COAL_BLOCK);
		
		this.arenaObjects[ITEM_SPAWNER][0] = itemSpawner;
		
		//Objective Points
		this.arenaObjects[OBJECTIVE_POINT] = new ItemStack[4];
		ItemStack diamondObjectivePoint = new ItemStack(Material.DIAMOND_ORE);
		ItemStack emeraldObjectivePoint = new ItemStack(Material.EMERALD_ORE);
		ItemStack goldObjectivePoint = new ItemStack(Material.GOLD_ORE);
		ItemStack ironObjectivePoint = new ItemStack(Material.IRON_ORE);
		
		this.arenaObjects[OBJECTIVE_POINT][0] = diamondObjectivePoint;
		this.arenaObjects[OBJECTIVE_POINT][1] = emeraldObjectivePoint;
		this.arenaObjects[OBJECTIVE_POINT][2] = goldObjectivePoint;
		this.arenaObjects[OBJECTIVE_POINT][3] = ironObjectivePoint;
		
		//Traps
		this.arenaObjects[TRAP] = new ItemStack[3];
		ItemStack explosionTrap = new ItemStack(Material.GOLD_BLOCK);
		ItemStack fireTrap = new ItemStack(Material.IRON_BLOCK);
		ItemStack poisonSnareTrap = new ItemStack(Material.EMERALD_BLOCK);
		
		this.arenaObjects[TRAP][0] = explosionTrap;
		this.arenaObjects[TRAP][1] = fireTrap;
		this.arenaObjects[TRAP][2] = poisonSnareTrap;
		
		//Gate
		this.arenaObjects[GATE] = new ItemStack[1];
		ItemStack gate = new ItemStack(Material.GOLD_SPADE);
		
		this.arenaObjects[GATE][0] = gate;		
		
		//Setting Display Names for all the objects
		this.setDisplayName(this.arenaObjects);
		
		//Putting Items in Player's Inventory
		int counter = INVENTORY_STARTING_PLACEMENT_SLOT;
		
		for(int i = 0; i < this.arenaObjects.length; i++) {
			for(int j = 0; j < this.arenaObjects[i].length; j++) {
				player.getInventory().setItem(counter++, this.arenaObjects[i][j]);
			}
		}	
	}
	
	private void setDisplayName(ItemStack[][] arenaObjects) {
		for(int i = 0; i < this.arenaObjects.length; i ++) {
			for(int j = 0; j < this.arenaObjects[i].length; j++) {
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
					trapMeta.setDisplayName("Place a trap.");
					arenaObjects[i][j].setItemMeta(trapMeta);
					break;
				case 3:
					ItemMeta gateMeta = arenaObjects[i][j].getItemMeta();
					gateMeta.setDisplayName("Place a redstone used to activate a gate.");
					arenaObjects[i][j].setItemMeta(gateMeta);
					break;
				default:
					return;
				}
			}
		}
	}

}
