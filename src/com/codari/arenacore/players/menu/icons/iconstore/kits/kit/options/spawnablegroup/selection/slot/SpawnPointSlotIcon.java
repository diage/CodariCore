package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.slot;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.objects.SpawnPoint;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SpawnPointSlotIcon extends ExecutableIcon {
	private int slotNumber;
	
	public SpawnPointSlotIcon(Combatant combatant, int slotNumber) {
		super(Material.WOOD_DOOR, combatant, "Slot " + (++slotNumber));
		this.slotNumber = slotNumber - 1;
	}

	@Override
	public void click() {
		Kit kit = KitListener.getKit(this.getCombatant());
		if(kit != null) {
			kit.setTool(this.slotNumber, SpawnPoint.SPAWN_POINT_NAME);
		}
	}

}
