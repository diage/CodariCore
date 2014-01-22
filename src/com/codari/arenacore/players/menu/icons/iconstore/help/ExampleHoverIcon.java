package com.codari.arenacore.players.menu.icons.iconstore.help;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.HoverIcon;

public class ExampleHoverIcon extends HoverIcon {

	public ExampleHoverIcon(Combatant combatant) {
		super(Material.SMOOTH_STAIRS, combatant, "Example Hover Icon");
		this.updateLore();
	}

	private void updateLore() {
		ItemMeta itemMeta = super.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("This is an example of a Hover Icon.");
		lore.add("Hover your mouse over the Icon and press a number (1-9).");
		lore.add("Press Shift + Right-Click in order to backspace.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
