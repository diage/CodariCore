package com.codari.apicore.itemdata;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.Codari;
import com.codari.api5.enchantment.CustomEnchantment;
import com.codari.api5.itemdata.ItemData;
import com.codari.api5.itemdata.ItemDataManager;

public class ItemDataManagerCore implements ItemDataManager {
	//-----Fields-----//
	private final Map<Integer, ItemData> dataMap;
	private final CustomEnchantment itemDataEnchantment;
	
	//-----Constructor-----//
	public ItemDataManagerCore() {
		this.dataMap = new HashMap<>();
		this.itemDataEnchantment = new ItemDataEnchantment();
		Codari.getEnchantmentManager().registerEnchantment(this.itemDataEnchantment);
	}
	
	@Override
	public void registerItemData(ItemData data) {
		if (this.dataMap.containsKey(data.getId())) {
			throw new IllegalArgumentException("Item data already exists with the id " + data.getId());
		}
		this.dataMap.put(data.getId(), data);
	}
	
	@Override
	public ItemStack setItemData(ItemStack item, int id) {
		if (item == null) {
			throw new IllegalArgumentException("Null ItemStack");
		}
		ItemData data = this.dataMap.get(id);
		if (data == null) {
			throw new IllegalArgumentException("No registered ItemData exists with the id " + id);
		}
		if (!data.canBeAppliedTo(item)) {
			throw new IllegalArgumentException("Can not apply item data id " + id + " to " + item.getType());
		}
		data.onApplication(item);
		item.addEnchantment(this.itemDataEnchantment, id);
		return item;
	}
	
	@Override
	public ItemStack setItemData(ItemStack item, ItemData data) {
		return this.setItemData(item, data.getId());
	}

	@Override
	public ItemData getItemData(ItemStack item) {
		if (item.containsEnchantment(this.itemDataEnchantment)) {
			return this.dataMap.get(item.getEnchantmentLevel(this.itemDataEnchantment));
		}
		return null;
	}
	
	//-----Item Data Enchantment-----//
	private final class ItemDataEnchantment extends CustomEnchantment {
		//-----Constructor-----//
		public ItemDataEnchantment() {
			super(25);
		}
		
		//-----Methods-----//
		@Override
		public boolean isVisible() {
			return false;
		}

		@Override
		public boolean canEnchantItem(ItemStack ignore) {
			return true;
		}

		@Override
		public boolean conflictsWith(Enchantment ignore) {
			return false;
		}

		@Override
		public EnchantmentTarget getItemTarget() {
			return EnchantmentTarget.ALL;
		}

		@Override
		public int getMaxLevel() {
			return Integer.MAX_VALUE;
		}

		@Override
		public String getName() {
			return "ITEM_DATA";
		}

		@Override
		public int getStartLevel() {
			return Integer.MIN_VALUE;
		}
	}
}