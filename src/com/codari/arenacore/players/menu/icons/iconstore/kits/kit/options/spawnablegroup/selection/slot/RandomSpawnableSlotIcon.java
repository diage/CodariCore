package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot;


import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.CurrentArenaObjectNameListener;

public class RandomSpawnableSlotIcon extends ExecutableIcon {
	private Combatant combatant;
	private Kit kit;
	private String randomSpawnableGroupName;
	private int slotNumber;
	
	public RandomSpawnableSlotIcon(Combatant combatant, Kit kit, String randomSpawnableGroupName, int slotNumber) {
		super(Material.BED, combatant, "Slot " + slotNumber);
		this.combatant = combatant;
		this.kit = kit;
		this.randomSpawnableGroupName = randomSpawnableGroupName;
		this.slotNumber = slotNumber;
	}

	@Override
	public void click() {
		String arenaObjectName = CurrentArenaObjectNameListener.currentRandomArenaObjectNames.get(this.combatant.getPlayerReference().getName());
		if(this.randomSpawnableGroupName != null && arenaObjectName != null) {
			this.kit.setTool(this.slotNumber, arenaObjectName, randomSpawnableGroupName);
		} else {
			this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "Failed to place object in toolbelt - "
					+ "Either the random spawnable group name or object is null!");
		}
	}
}
