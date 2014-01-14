package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.CurrentArenaObjectNameListener;

public class RandomSpawnableSlotIcon extends ExecutableIcon {
	private Combatant combatant;
	private String randomSpawnableGroupName;
	private int slotNumber;
	
	public RandomSpawnableSlotIcon(Combatant combatant, String randomSpawnableGroupName, int slotNumber) {
		super(Material.BED, combatant, "Slot " + slotNumber + 1);
		this.combatant = combatant;
		this.randomSpawnableGroupName = randomSpawnableGroupName;
		this.slotNumber = slotNumber;
	}

	@Override
	public void click() {
		String arenaObjectName = CurrentArenaObjectNameListener.currentRandomArenaObjectNames.get(this.combatant.getPlayerReference().getName());
		if(this.randomSpawnableGroupName != null && arenaObjectName != null) {
			Kit kit = KitListener.getKit(this.getCombatant());
			if(kit != null) {		
				kit.setTool(this.slotNumber, arenaObjectName, randomSpawnableGroupName);
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + "Kit is null!");	//TODO - for testing
			}
		} else {
			this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "Failed to place object in toolbelt - "
					+ "Either the random spawnable group name or object is null!");
		}
	}
}
