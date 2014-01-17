package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.SaveSpawnableGroupIconListener;

public class SaveIcon extends ExecutableIcon {
	private Kit kit;
	
	public SaveIcon(Combatant combatant, Kit kit) {
		super(Material.REDSTONE_BLOCK, combatant, "Save");
		this.kit = kit;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(SaveSpawnableGroupIconListener.requestedSpawnableGroupNames.containsKey(player.getName())) {
			String groupName = SaveSpawnableGroupIconListener.requestedSpawnableGroupNames.get(player.getName());
			this.kit.createGroupName(groupName);
			if(this.kit.createRandomTimeLineGroup()) {
				player.sendMessage(ChatColor.GREEN + "You have successfully created a random spawnable group!");
				SaveSpawnableGroupIconListener.requestedSpawnableGroupNames.remove(player.getName());
				((CombatantCore)this.getCombatant()).getDynamicMenuManager().addSpawnableGroupIcon(this.kit, groupName);
			} else {
				player.sendMessage(ChatColor.RED + "Failed to create a random spawnable group! You must fill out at least a delay time.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for the spawnable group first!");
		}
	}
}
