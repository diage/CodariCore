package com.codari.apicore;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.api5.Codari;
import com.codari.api5.itemdata.ItemData;

public class ItemDataTest implements Listener {
	TestData td = new TestData();
	AlsoTestData atd = new AlsoTestData();
	
	public ItemDataTest() {
		Codari.getItemDataManager().registerItemData(td);
		Codari.getItemDataManager().registerItemData(atd);
	}
	
	@EventHandler
	public void test(PlayerInteractEvent e) {
		ItemStack item = e.getItem();
		if (e.getItem() != null) {
			switch (e.getAction()) {
			case LEFT_CLICK_AIR:
			case LEFT_CLICK_BLOCK:
				Codari.getItemDataManager().setItemData(item, td);
				break;
			case RIGHT_CLICK_AIR:
			case RIGHT_CLICK_BLOCK:
				Codari.getItemDataManager().setItemData(item, atd);
				break;
			default:
				break;
			}
			Bukkit.broadcastMessage(Codari.getItemDataManager().getItemData(item).toString());
		}
	}
	
	private final class TestData extends ItemData {
		public TestData() {
			super(25);
		}
		
		@Override
		public String toString() {
			return "I AM DATA";
		}

		@Override
		public boolean canBeAppliedTo(ItemStack item) {
			return true;
		}
		
		@Override
		public void onApplication(ItemStack item) {
			ItemMeta meta = Bukkit.getItemFactory().getItemMeta(item.getType());
			meta.setDisplayName("DATA TEST");
			item.setItemMeta(meta);
		}
	}
	
	private final class AlsoTestData extends ItemData {
		public AlsoTestData() {
			super(255);
		}
		
		@Override
		public String toString() {
			return "I AM ALSO DATA";
		}

		@Override
		public boolean canBeAppliedTo(ItemStack item) {
			return true;
		}
		
		@Override
		public void onApplication(ItemStack item) {
			ItemMeta meta = Bukkit.getItemFactory().getItemMeta(item.getType());
			meta.setDisplayName("ALSO DATA TEST");
			item.setItemMeta(meta);
		}
	}
}