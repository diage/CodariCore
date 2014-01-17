package com.codari.arenacore.players.menu.icons.iconstore.guilds.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.menus.Menu;

public class GuildIcon extends MenuIcon {

	public GuildIcon(Combatant combatant, Menu menu, String guildName) {
		super(Material.DIAMOND_BLOCK, combatant, menu, guildName);
	}

}
