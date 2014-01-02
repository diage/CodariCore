package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SaveIcon extends ExecutableIcon implements Listener {
	private Kit kit;
	
	public SaveIcon(Combatant combatant, Kit kit) {
		super(Material.REDSTONE_BLOCK, combatant, "Save");
		this.kit = kit;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(this.kit.createRandomTimeLineGroup()) {
			player.sendMessage(ChatColor.GREEN + "You have successfully created a random spawnable group!");
		} else {
			player.sendMessage(ChatColor.RED + "Failed to create a random spawnable group! You must fill out at least a name and a delay time.");
		}
	}
}
