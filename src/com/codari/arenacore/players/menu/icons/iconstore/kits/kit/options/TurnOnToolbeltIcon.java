package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class TurnOnToolbeltIcon extends ExecutableIcon {
	private String kitName;
	
	public TurnOnToolbeltIcon(Combatant combatant, String kitName) {
		super(Material.REDSTONE_BLOCK, combatant, "Turn on Toolbelt");
		this.kitName = kitName;
	}

	@Override
	public void click() {
		if(!((CombatantCore)this.getCombatant()).getKitManager().isToolBarEnabled()) {
			((CombatantCore)this.getCombatant()).getKitManager().enableToolBar(this.kitName);
		}
	}
}
