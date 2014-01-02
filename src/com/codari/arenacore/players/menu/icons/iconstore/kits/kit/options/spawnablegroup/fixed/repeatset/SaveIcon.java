package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SaveIcon extends ExecutableIcon {
	private Kit kit;
	
	public SaveIcon(Combatant combatant, Kit kit) {
		super(Material.OBSIDIAN, combatant, "Save");
		this.kit = kit;
	}

	@Override
	public void click() {
		this.kit.createFixedRepeatTime();
	}
}
