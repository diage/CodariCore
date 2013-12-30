package com.codari.arenacore.players.menu.icons.iconstore.utilitymenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class ExitIcon extends ExecutableIcon{

	public ExitIcon(Material material, Combatant combatant) {
		super(material, combatant, "Exit");
	}

	@Override
	public void click() {
		((CombatantCore) this.getCombatant()).getMenuManager().exitMenu();
		Bukkit.getPlayer(this.playerName).closeInventory();
	}
}
