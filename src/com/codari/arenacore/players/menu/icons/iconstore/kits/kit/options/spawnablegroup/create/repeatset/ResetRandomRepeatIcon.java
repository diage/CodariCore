package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupRepeatSet;

public class ResetRandomRepeatIcon extends ExecutableIcon {
	private SpawnableGroupRepeatSet spawnableGroupRepeatSet;
	
	public ResetRandomRepeatIcon(Combatant combatant, SpawnableGroupRepeatSet spawnableGroupRepeatSet) {
		super(Material.SUGAR_CANE_BLOCK, combatant, "Reset Random Repeat Time");
		this.spawnableGroupRepeatSet = spawnableGroupRepeatSet;
		this.updateLore();
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		KitListener.getKit(this.getCombatant()).resetRandomRepeatTime();
		this.spawnableGroupRepeatSet.clearLore();
		player.sendMessage(ChatColor.BLUE + "You have reset the Random Repeat Time.");
	}
	
	private void updateLore() {
		ItemMeta itemMeta = super.getItemMeta();
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.BLUE + "Set Repeat Time to null.");
		itemMeta.setLore(lore);
		super.setItemMeta(itemMeta);
	}
}
