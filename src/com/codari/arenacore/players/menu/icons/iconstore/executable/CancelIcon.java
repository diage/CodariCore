package com.codari.arenacore.players.menu.icons.iconstore.executable;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class CancelIcon extends ExecutableIcon{

	public CancelIcon(Material material, Combatant combatant, String displayName) {
		super(material, combatant, displayName);
	}

	@Override
	public void click() {
		((CombatantCore) this.getCombatant()).getMenuManager().exitMenu();
		Bukkit.getPlayer(this.playerName).closeInventory();
	}
}
