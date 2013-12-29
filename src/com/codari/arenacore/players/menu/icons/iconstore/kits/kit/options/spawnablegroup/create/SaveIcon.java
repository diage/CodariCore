package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SaveIcon extends ExecutableIcon implements Listener {
	private String kitName;
	private Combatant combatant;
	
	public SaveIcon(Combatant combatant, String kitName) {
		super(Material.REDSTONE_BLOCK, combatant, "Save");
		this.kitName = kitName;
	}

	@Override
	public void click() {
		Player player = this.combatant.getPlayer();
		if(((CombatantCore)combatant).getKitManager().selectKit(this.kitName).createRandomTimeLineGroup()) {
			player.sendMessage(ChatColor.GREEN + "You have successfully created a random spawnable group!");
		} else {
			player.sendMessage(ChatColor.RED + "Failed to create a random spawnable group! You must fill out a name and a delay time.");
		}
	}
}
