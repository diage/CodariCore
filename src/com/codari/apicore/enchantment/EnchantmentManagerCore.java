package com.codari.apicore.enchantment;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.CodariI;
import com.codari.api5.enchantment.CustomEnchantment;
import com.codari.api5.enchantment.EnchantmentManager;
import com.codari.api5.util.reflect.Reflector;
import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;

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
	
	public void packetStuff() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
				new PacketAdapter(CodariI.INSTANCE, ConnectionSide.SERVER_SIDE,
						Packets.Server.SET_SLOT, Packets.Server.WINDOW_ITEMS) {        
			@Override
			public void onPacketSending(PacketEvent event) {
				PacketContainer packet = event.getPacket();
				boolean visibleCustom = false;
				switch (event.getPacketID()) {
				case Packets.Server.SET_SLOT:
					ItemStack item = packet.getItemModifier().read(0);
					if (item != null) {
						for (CustomEnchantment enchantment : customEnchantments) {
							if (item.containsEnchantment(enchantment)) {
								item.removeEnchantment(enchantment);
								if (enchantment.isVisible()) {
									visibleCustom = true;
								}
							}
						}
						if (visibleCustom) {
							NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(item);
							compound.put(NbtFactory.ofList("ench"));
						}
					}
					break;
				case Packets.Server.WINDOW_ITEMS:
					ItemStack[] elements = packet.getItemArrayModifier().read(0);
					for (ItemStack elementItem : elements) {
						if (elementItem != null) {
							for (CustomEnchantment enchantment : customEnchantments) {
								if (elementItem.containsEnchantment(enchantment)) {
									elementItem.removeEnchantment(enchantment);
									if (enchantment.isVisible()) {
										visibleCustom = true;
									}
								}
							}
							if (visibleCustom) {
								NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(elementItem);
								compound.put(NbtFactory.ofList("ench"));
							}
						}
					}
					break;
				}
			}
		});
	}
	
	//-----Methods-----//
	@Override
	public void registerEnchantment(CustomEnchantment enchantment) {
		Reflector.writeFieldObject(this.acceptingNew, null, true);
		Enchantment.registerEnchantment(enchantment);
		this.customEnchantments.add(enchantment);
		Reflector.writeFieldObject(this.acceptingNew, null, false);
	}
	
	@Override
	public boolean containsCustomEnchantment(ItemStack itemStack) {
		for (CustomEnchantment enchantment : this.customEnchantments) {
			if (itemStack.containsEnchantment(enchantment)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void unregisterCustomEnchantments() {
		for (CustomEnchantment enchantment : this.customEnchantments) {
			this.byId.invoke("remove", enchantment.getId());
			this.byName.invoke("remove", enchantment.getName());
		}
		this.customEnchantments.clear();
	}
}