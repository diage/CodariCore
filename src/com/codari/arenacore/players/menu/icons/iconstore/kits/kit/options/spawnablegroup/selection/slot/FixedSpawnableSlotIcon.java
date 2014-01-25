package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.api5.util.Time;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class FixedSpawnableSlotIcon extends ExecutableIcon{
	private String arenaObjectName;
	private int slotNumber;

	public FixedSpawnableSlotIcon(Combatant combatant, String arenaObjectName, int slotNumber) {
		super(Material.BED, combatant, "Slot " + (++slotNumber));
		this.arenaObjectName = arenaObjectName;
		this.slotNumber = slotNumber - 1;
	}

	@Override
	public void click() {
		if(this.arenaObjectName != null) {
			Kit kit = KitListener.getKit(this.getCombatant());
			if(kit != null) {
				Time delayTime = kit.getFixedDelayTime();
				Time repeatTime = kit.getFixedRepeatTime();
				if(repeatTime == null && delayTime == null) {
					kit.setTool(this.slotNumber, this.arenaObjectName);
				} else if(repeatTime == null) {
					kit.setTool(this.slotNumber, this.arenaObjectName, delayTime.toString());
				} else {
					kit.setTool(this.slotNumber, this.arenaObjectName, delayTime.toString(), repeatTime.toString());
				}
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + "Kit is null!");	//TODO
			}
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "Fixed arena object is null!"); //TODO
		}

	}
}
