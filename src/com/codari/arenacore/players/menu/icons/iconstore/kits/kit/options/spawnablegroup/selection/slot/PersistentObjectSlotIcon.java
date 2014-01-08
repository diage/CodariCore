package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.api5.util.Time;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class PersistentObjectSlotIcon extends ExecutableIcon {
	private Kit kit;
	private String arenaObjectName;
	private Time delayTime;
	private boolean override;
	private int slotNumber;
	
	public PersistentObjectSlotIcon(Combatant combatant, Kit kit, String arenaObjectName, Time delayTime, boolean override, int slotNumber) {
		super(Material.BED, combatant, "Slot " + slotNumber + 1);
		this.kit = kit;
		this.arenaObjectName = arenaObjectName;
		this.delayTime = delayTime;
		this.override = override;
		this.slotNumber = slotNumber;
	}

	@Override
	public void click() {
		if(this.arenaObjectName != null) {
			if(this.delayTime == null) {
				this.kit.setTool(this.slotNumber, this.arenaObjectName);
			} else {
				this.kit.setTool(this.slotNumber, this.arenaObjectName, this.delayTime.toString(), Boolean.toString(this.override));
			}
		} else {
			this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "Failed to place object in toolbelt - "
					+ "The persistent object is null!");
		}	
	}	
}
