package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot;


import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class RandomSpawnableSlotIcon extends ExecutableIcon {
	private Kit kit;
	private String randomSpawnableGroupName;
	String arenaObjectName;
	private int slotNumber;
	
	public RandomSpawnableSlotIcon(Combatant combatant, Kit kit, String randomSpawnableGroupName, String arenaObjectName, int slotNumber) {
		super(Material.BED, combatant, "Slot " + slotNumber);
		this.kit = kit;
		this.randomSpawnableGroupName = randomSpawnableGroupName;
		this.arenaObjectName = arenaObjectName;
		this.slotNumber = slotNumber;
	}

	@Override
	public void click() {
		if(this.randomSpawnableGroupName != null && this.arenaObjectName != null) {
			this.kit.setTool(this.slotNumber, this.arenaObjectName, randomSpawnableGroupName);
		} else {
			this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "Failed to place object in toolbelt - "
					+ "Either the random spawnable group name or object is null!");
		}
	}
}
