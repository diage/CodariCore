package com.codari.apicore.enchantment;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.enchantments.Enchantment;

import com.codari.api5.enchantment.CustomEnchantment;
import com.codari.api5.enchantment.EnchantmentManager;
import com.codari.api5.util.reflect.Reflector;

public class EnchantmentManagerCore implements EnchantmentManager {
	//-----Fields-----//
	private final Set<CustomEnchantment> customEnchantments;
	private final Field acceptingNew;
	private final Reflector byId;
	private final Reflector byName;
	
	//-----Constructor-----//
	public EnchantmentManagerCore() {
		this.customEnchantments = new HashSet<>();
		this.acceptingNew = Reflector.getFieldObject(Enchantment.class, "acceptingNew");
		this.byId = Reflector.readStaticField(Enchantment.class, "byId");
		this.byName = Reflector.readStaticField(Enchantment.class, "byName");
	}
	
	//-----Methods-----//
	@Override
	public void registerEnchantment(CustomEnchantment enchantment) {
		Reflector.writeFieldObject(this.acceptingNew, null, true);
		Enchantment.registerEnchantment(enchantment);
		this.customEnchantments.add(enchantment);
		Reflector.writeFieldObject(this.acceptingNew, null, false);
	}
	
	@SuppressWarnings("deprecation")
	public void unregisterCustomEnchantments() {
		for (CustomEnchantment enchantment : this.customEnchantments) {
			this.byId.invoke("remove", enchantment.getId());
			this.byName.invoke("remove", enchantment.getName());
		}
	}
}