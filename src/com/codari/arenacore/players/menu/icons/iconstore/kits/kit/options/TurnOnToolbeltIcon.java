package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class TurnOnToolbeltIcon extends ExecutableIcon {
	
	public TurnOnToolbeltIcon(Combatant combatant) {
		super(Material.COOKED_CHICKEN, combatant, "Turn on Toolbelt");
	}

	@Override
	public void click() {
		if(!((CombatantCore)this.getCombatant()).getToolbarManager().isToolBarEnabled()) {
			((CombatantCore)this.getCombatant()).getToolbarManager().enableToolBar();
		}
	}
}
