package com.codari.arenacore.players.menu.icons.iconstore.help;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;

public class InstructionsIconOne extends MenuIcon {	//FIXME - should be a Display Icon

	public InstructionsIconOne(Combatant combatant) {
		super(Material.COBBLESTONE, combatant, null, "Introduction");
		this.updateLore();
	}

	private void updateLore() {
		ItemMeta itemMeta = super.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("This Menu System allows you create Arenas.");
		lore.add("Arenas Builders are represented by Kits.");
		lore.add("Kits can be constructed with Kit Builders");
		lore.add("Kit Builders are made with settings such");
		lore.add("as match duration or team size.");
		lore.add("You can also create your own Team / Guild");
		lore.add("Roles must be created in the Role Menu");
		lore.add("before you could add them to arenas.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
