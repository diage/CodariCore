package com.codari.arenacore.players.menu.icons.iconstore.help;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class ExampleExecutableIcon extends ExecutableIcon {

	public ExampleExecutableIcon(Combatant combatant) {
		super(Material.STONE_BUTTON, combatant, "Example Executable Icon");
		this.updateLore();
	}

	@Override
	public void click() {
		this.getCombatant().getPlayer().sendMessage("You just clicked an Executable Icon!");
	}

	private void updateLore() {
		ItemMeta itemMeta = super.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add("This is an example of an Executable Icon.");
		lore.add("Clicking on this Icon executes an action.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
