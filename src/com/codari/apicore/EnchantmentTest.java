package com.codari.apicore;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.Codari;
import com.codari.api5.enchantment.CustomEnchantment;

public class EnchantmentTest implements Listener {
	CustomEnchantment visibleTest;
	CustomEnchantment nonVisibleTest;
	public EnchantmentTest() {
		this.visibleTest = new CustomEnchantment(25) {
			@Override
			public boolean isVisible() {
				return true;
			}

			@Override
			public boolean canEnchantItem(ItemStack item) {
				return true;
			}

			@Override
			public boolean conflictsWith(Enchantment other) {
				return false;
			}

			@Override
			public EnchantmentTarget getItemTarget() {
				return EnchantmentTarget.ALL;
			}

			@Override
			public int getMaxLevel() {
				return 25;
			}

			@Override
			public String getName() {
				return "VISIBLE";
			}

			@Override
			public int getStartLevel() {
				return 1;
			}	
		};
		this.nonVisibleTest = new CustomEnchantment(26) {
			@Override
			public boolean isVisible() {
				return false;
			}

			@Override
			public boolean canEnchantItem(ItemStack item) {
				return true;
			}

			@Override
			public boolean conflictsWith(Enchantment other) {
				return false;
			}

			@Override
			public EnchantmentTarget getItemTarget() {
				return EnchantmentTarget.ALL;
			}

			@Override
			public int getMaxLevel() {
				return 25;
			}

			@Override
			public String getName() {
				return "NON_VISIBLE";
			}

			@Override
			public int getStartLevel() {
				return 1;
			}	
		};
		Codari.getEnchantmentManager().registerEnchantment(visibleTest);
		Codari.getEnchantmentManager().registerEnchantment(nonVisibleTest);
	}
	
	@EventHandler
	public void test(PlayerInteractEvent e) {
		ItemStack item = e.getItem();
		if (e.getItem() != null) {
			switch (e.getAction()) {
			case LEFT_CLICK_AIR:
			case LEFT_CLICK_BLOCK:
				item.removeEnchantment(nonVisibleTest);
				item.addEnchantment(visibleTest, 25);
				break;
			case RIGHT_CLICK_AIR:
			case RIGHT_CLICK_BLOCK:
				item.removeEnchantment(visibleTest);
				item.addEnchantment(nonVisibleTest, 25);
				break;
			default:
				break;
			}
		}
	}
}