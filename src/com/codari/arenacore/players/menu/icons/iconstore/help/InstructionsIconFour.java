package com.codari.arenacore.players.menu.icons.iconstore.help;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;

public class InstructionsIconFour extends MenuIcon {	//FIXME - should be a Display Icon

	public InstructionsIconFour(Combatant combatant) {
		super(Material.COBBLESTONE, combatant, null, "Icon Types");
		this.updateLore();
	}

	private void updateLore() {
		ItemMeta itemMeta = super.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("The following are some Icons you will");
		lore.add("run into while using the Menu.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
