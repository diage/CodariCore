package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.api5.util.Time;
import com.codari.arena5.objects.persistant.PersistentObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class PersistentObjectSlotIcon extends ExecutableIcon {
	private Kit kit;
	private PersistentObject persistentObject;
	private Time delayTime;
	private boolean override;
	private int slotNumber;
	
	public PersistentObjectSlotIcon(Combatant combatant, Kit kit, PersistentObject persistentObject, Time delayTime, boolean override, int slotNumber) {
		super(Material.BED, combatant, "Slot " + slotNumber);
		this.kit = kit;
		this.persistentObject = persistentObject;
		this.delayTime = delayTime;
		this.override = override;
		this.slotNumber = slotNumber;
	}

	@Override
	public void click() {
		if(this.persistentObject != null) {
			if(this.delayTime == null) {
				this.kit.setTool(this.slotNumber, this.persistentObject.getName());
			} else {
				this.kit.setTool(this.slotNumber, this.persistentObject.getName(), this.delayTime.toString(), Boolean.toString(this.override));
			}
		} else {
			this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "Failed to place object in toolbelt - "
					+ "The persistent object is null!");
		}	
	}	
}
