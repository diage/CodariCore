package com.codari.arenacore.players.menu.icons.iconstore.guilds.options;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.guilds.GuildBuilder;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class LeaveGuildIcon extends ExecutableIcon {
	
	public LeaveGuildIcon(Combatant combatant) {
		super(Material.STATIONARY_LAVA, combatant, "Leave Guild");
	}

	@Override
	public void click() {
		if(((CombatantCore) this.getCombatant()).getGuild() != null) {
			((CombatantCore) this.getCombatant()).getDynamicMenuManager().addNoGuildIcons();
			GuildBuilder.removePlayer(this.getCombatant());
			this.getCombatant().getPlayer().sendMessage(ChatColor.BLUE + "You have left your guild.");
		}
	}

}
