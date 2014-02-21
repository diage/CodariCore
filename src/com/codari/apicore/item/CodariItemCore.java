package com.codari.apicore.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.codari.apicore.item.asset.StackAsset;
import com.codari.apicore.item.asset.useassets.UseAsset;
import com.codari.apicore.item.assetmaster.DefinedAsset;
import com.codari.apicore.item.assetmaster.SlotType;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.ItemAsset;
import com.codari.arena5.players.combatants.Combatant;

/* Damage Values
 * Construct the Listener PlayerInteractEvent, PlayerHitEvent, etc.
 * Damage - Cancel the player damage event, and use ours instead
 * Item xp - whenever a player is given experience, it will cancel that and will give it to our item instead
 * Item level up event - may or may not need to construct a new item
 * Figure out how to deal with attributes by contacting Soren */
public class CodariItemCore implements CodariItem {

	//-----Fields-----//
	private final Combatant combatant;
	private final ItemAsset[] assets;
	private final ItemStack itemStack;
	public List<String> displayLore;
	
	//-----Constructor-----//
	public CodariItemCore(Combatant combatant, ItemStack itemStack) {
		this.combatant = combatant;
		this.assets = new ItemAsset[SlotType.TOTAL_SLOTS];
		this.itemStack = itemStack;
		this.displayLore = new ArrayList<>();
	}
	
	//-----Methods-----//
	@Override
	public Combatant getCombatant() {
		return this.combatant;
	}
	
	public ItemStack getItemStack() {
		return this.itemStack;
	}
	
	public void setAssets(List<DefinedAsset> assets) throws Exception {
		List<String> lore = new ArrayList<>();
		for(DefinedAsset definedAsset : assets) {
			ItemAsset itemAsset = definedAsset.getItem();
			SlotType slotType = definedAsset.getSlotType();
			
			switch(slotType) {
			case ATTRIBUTE:
				if(this.assets[0] == null) {
					this.assets[0] = itemAsset;
					break;
				} else if(this.assets[1] == null) {
					this.assets[1] = itemAsset;
					break;
				} else if(this.assets[2] == null) {
					this.assets[2] = itemAsset;
					break;
				} 
				throw new Exception("Couldn't add Item asset! The slot " + slotType + " is full!");
			case INCREMENTAL:
			case PREFIX:
			case STACK:
			case SUFFIX:
			case USE:
				if(this.assets[slotType.getSlotValue()] == null) {
					this.assets[slotType.getSlotValue()] = itemAsset;
					break;
				}
				throw new Exception("Couldn't add Item asset! The slot " + slotType + " is full!");
			}
			lore.add(itemAsset.serializeToString());
		}
		
		if(this.assets[SlotType.STACK.getSlotValue()] == null) {
			this.addAsset(new StackAsset(this, "" + 1), SlotType.STACK);
		}
		this.updateLore();
	}
	
	public void addAsset(ItemAsset itemAsset, SlotType slotType) throws Exception {
		switch(slotType) {
		case ATTRIBUTE:
			if(this.assets[0] == null) {
				this.assets[0] = itemAsset;
				break;
			} else if(this.assets[1] == null) {
				this.assets[1] = itemAsset;
				break;
			} else if(this.assets[2] == null) {
				this.assets[2] = itemAsset;
				break;
			} 
			throw new Exception("Couldn't add Item asset! The slot " + slotType + " is full!");
		case INCREMENTAL:
		case PREFIX:
		case STACK:
		case SUFFIX:
		case USE:
			if(this.assets[slotType.getSlotValue()] == null) {
				this.assets[slotType.getSlotValue()] = itemAsset;
				break;
			}
			throw new Exception("Couldn't add Item asset! The slot " + slotType + " is full!");
		}
		this.updateLore();
	}

	@Override
	public void attack(Combatant enemy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStackSize() {
		return ((StackAsset)this.assets[SlotType.STACK.getSlotValue()]).getStackSize();
	}

	@Override
	public void addStackSize(int size) {
		if(size > 0) {
			((StackAsset)this.assets[SlotType.STACK.getSlotValue()]).increment(size);
		} else {
			if(!((StackAsset)this.assets[SlotType.STACK.getSlotValue()]).decrement(size)) {
				this.combatant.getPlayer().getInventory().remove(this.itemStack);
			}
		}
		this.updateLore();
	}

	@Override
	public void getAttributes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void levelUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addExp(int exp) {
		// TODO Auto-generated method stub
		this.updateLore();
	}

	@Override
	public void use() {
		((UseAsset)this.assets[SlotType.USE.getSlotValue()]).use();		
	}
	
	public List<String> getDisplayLore() {
		return new ArrayList<>(this.displayLore);
	}
	
	@SuppressWarnings("deprecation")
	private void updateLore() {
		this.displayLore.clear();
		for(ItemAsset asset : this.assets) {
			if(asset != null) {
				this.displayLore.addAll(asset.getLore());
			}
		}
		this.combatant.getPlayer().updateInventory();
	}	
}
