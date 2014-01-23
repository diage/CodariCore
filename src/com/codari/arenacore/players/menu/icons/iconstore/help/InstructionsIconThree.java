package com.codari.arenacore.players.menu.icons.iconstore.help;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;

public class InstructionsIconThree extends MenuIcon {	//FIXME - should be a Display Icon

	public InstructionsIconThree(Combatant combatant) {
		super(Material.COBBLESTONE, combatant, null, "Placing Arena Objects");
		this.updateLore();
	}

	private void updateLore() {
		ItemMeta itemMeta = super.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("While creating your Arena Objects you'll notice");
		lore.add("the ability to select a slot. Clicking on a slot");
		lore.add("will place that Arena Object on your hotbar.");
		lore.add("Make sure your hotbar is enabled from the Kit");
		lore.add("Options Menu. You must turn the hotbar off and on");
		lore.add("to see any added Arena Objects. Right click w/");
		lore.add("the Arena Object equipped in order to place it down.");
		lore.add("Finalizing the arena will give Teams the ability");
		lore.add("to join its queue.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
