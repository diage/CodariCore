package com.codari.arenacore.players.menu.icons.iconstore.help;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;

public class InstructionsIconTwo extends MenuIcon {	//FIXME - should be a Display Icon

	public InstructionsIconTwo(Combatant combatant) {
		super(Material.COBBLESTONE, combatant, null, "Adding Arena Objects");
		this.updateLore();
	}

	private void updateLore() {
		ItemMeta itemMeta = super.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("The arena has three different types of objects:");
		lore.add("of objects: Random Spawnable, Fixed Spawnable, ");
		lore.add("and Immediate Persistent. Most of the objects ");
		lore.add("at the moment are random spawnable. In order to");
		lore.add("add a RS object, you must first create a Spwanable");
		lore.add("Group. Spawnable Groups are randomly chosen by the");
		lore.add("arena to spawn an item. They can have delay times");
		lore.add("and repeat times.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
