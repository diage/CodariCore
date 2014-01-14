package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.BuildingEndEvent;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class TurnOffToolbeltIcon extends ExecutableIcon {		
	public TurnOffToolbeltIcon(Combatant combatant) {
		super(Material.OBSIDIAN, combatant, "Turn off toolbelt");
	}

	@Override
	public void click() {
		if(((CombatantCore)this.getCombatant()).getKitManager().isToolBarEnabled()) {
			((CombatantCore)this.getCombatant()).getKitManager().disableToolBar();
			BuildingEndEvent e = new BuildingEndEvent(this.getCombatant());
			Bukkit.getPluginManager().callEvent(e);
		}
	}

}
