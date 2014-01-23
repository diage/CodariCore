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
		lore.add("While creating your Arena Objects you'll");
		lore.add("notice the ability to select a slot.");  
		lore.add("Clicking on a slot will place that Arena"); 
		lore.add("Object on your hotbar. Make sure your"); 
		lore.add("hotbar is enabled from the Kit Options Menu."); 
		lore.add("You must turn the hotbar off and on to see"); 
		lore.add("any added Arena Objects. Right click with");
		lore.add("the Arena Object equipped in order to place"); 
		lore.add("it down. Finalizing the arena will give"); 
		lore.add("Teams the ability to join its queue.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
