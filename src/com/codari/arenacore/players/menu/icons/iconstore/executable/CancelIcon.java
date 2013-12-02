package com.codari.arenacore.players.menu.icons.iconstore.executable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class CancelIcon extends ExecutableIcon{

	public CancelIcon(Material material, Player player, String displayName) {
		super(material, player, displayName);
	}

	@Override
	public void click() {
		((CombatantCore) Codari.getArenaManager().getCombatant(this.playerName)).getMenuManager().exitMenu();
		Bukkit.getPlayer(this.playerName).closeInventory();
	}
}
