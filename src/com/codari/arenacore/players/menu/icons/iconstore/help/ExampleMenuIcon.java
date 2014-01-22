package com.codari.arenacore.players.menu.icons.iconstore.help;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class ExampleMenuIcon extends MenuIcon {

	public ExampleMenuIcon(Combatant combatant, Menu menu) {
		super(Material.WATER_BUCKET, combatant, menu, "Example Menu Icon");
		this.updateLore();
	}

	private void updateLore() {
		ItemMeta itemMeta = super.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("This is an example of a Menu Icon.");
		lore.add("Clicking on this Icon brings you to a new Menu.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
