package com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.builderselection;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class KitBuilderIcon extends ExecutableIcon {
	private String kitName;
	
	//Make sure the displayName is the same as the name of the kitBuilder!!
	public KitBuilderIcon(Combatant combatant, String displayName) {
		super(Material.OBSIDIAN, combatant, displayName);
		this.kitName = displayName;
	}

	@Override
	public void click() {
		((CombatantCore)this.getCombatant()).getKitManager().getKit(this.kitName);
	}
}
