package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.api5.util.Time;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class FixedSpawnableSlotIcon extends ExecutableIcon{
	private Kit kit;
	private String arenaObjectName;
	private Time delayTime;
	private Time repeatTime;
	private int slotNumber;
	
	public FixedSpawnableSlotIcon(Combatant combatant, Kit kit, String arenaObjectName, Time delayTime, Time repeatTime, int slotNumber) {
		super(Material.BED, combatant, "Slot " + (slotNumber + 1));
		this.kit = kit;
		this.arenaObjectName = arenaObjectName;
		this.delayTime = delayTime;
		this.repeatTime = repeatTime;
		this.slotNumber = slotNumber;
	}

	@Override
	public void click() {
		if(this.arenaObjectName != null && this.delayTime != null) {
			if(this.repeatTime == null) {
				this.kit.setTool(this.slotNumber, this.arenaObjectName, this.delayTime.toString());
			} else {
				this.kit.setTool(this.slotNumber, this.arenaObjectName, this.delayTime.toString(), this.repeatTime.toString());
			}
		} else {
			this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "Failed to place object in toolbelt - "
					+ "Either the fixed spawnable object or delay time is null!");
		}
		
	}
}
