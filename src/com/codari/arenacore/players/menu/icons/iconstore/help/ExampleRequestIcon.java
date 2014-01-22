package com.codari.arenacore.players.menu.icons.iconstore.help;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class ExampleRequestIcon extends RequestIcon {

	public ExampleRequestIcon(Combatant combatant) {
		super(Material.STRING, combatant, "Example Request Icon");
		this.updateLore();
	}

	@Override
	public String getConversationString() {
		return "You will be requested to type in further information. If you don't respond within 10 seconds, the Icon won't take further "
				+ "input until you click on it again. Make sure to open your Menu after you respond.";
	}

	private void updateLore() {
		ItemMeta itemMeta = super.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("This is an example of a Request Icon.");
		lore.add("Clicking on this Icon will create a Conversation Factory ");
		lore.add("which will ask for further input.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
