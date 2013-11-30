package com.codari.arenacore.players.menu.icons.structure;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class SelectionEnchantment extends Enchantment {
	//----Fields-----//
	public final static Enchantment INSTANCE = new SelectionEnchantment();

	public SelectionEnchantment() {
		super(123456);
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
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
		return "Selection Enchantment";
	}

	@Override
	public int getStartLevel() {
		return Integer.MIN_VALUE;
	}
}
