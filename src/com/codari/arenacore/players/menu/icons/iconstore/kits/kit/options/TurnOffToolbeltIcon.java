package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
//import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class TurnOffToolbeltIcon extends ExecutableIcon {
	//private Combatant combatant;
	
	public TurnOffToolbeltIcon(Combatant combatant) {
		super(Material.OBSIDIAN, combatant, "Turn off toolbelt");
		//this.combatant = combatant;
	}

	@Override
	public void click() {
	}

}
