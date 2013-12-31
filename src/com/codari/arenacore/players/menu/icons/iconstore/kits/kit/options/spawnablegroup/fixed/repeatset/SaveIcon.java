package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SaveIcon extends ExecutableIcon {
	private String kitName;
	private Combatant combatant;
	
	public SaveIcon(Combatant combatant, String kitName) {
		super(Material.OBSIDIAN, combatant, "Save");
		this.combatant = combatant;
		this.kitName = kitName;
	}

	@Override
	public void click() {
		((CombatantCore)this.combatant).getKitManager().getKit(this.kitName).createFixedRepeatTime();
	}
}
