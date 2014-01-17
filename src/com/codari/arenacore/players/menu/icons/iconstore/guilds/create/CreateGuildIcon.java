package com.codari.arenacore.players.menu.icons.iconstore.guilds.create;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.guilds.GuildBuilder;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.GuildMenuListener;

public class CreateGuildIcon extends ExecutableIcon{
	
	public CreateGuildIcon(Combatant combatant) {
		super(Material.EMERALD_BLOCK, combatant, "Create Guild");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(GuildMenuListener.requestedGuildNames.containsKey(player.getName())) {
			if(((CombatantCore) this.getCombatant()).getGuild() == null) {
				String guildName = GuildMenuListener.requestedGuildNames.get(player.getName());
				if(!Codari.getGuildManager().containsGuild(guildName)) {
					GuildBuilder.createNewGuild(this.getCombatant(), guildName);
					player.sendMessage(ChatColor.GREEN + "You have created a new guild named " + "\"" + guildName + "\"");
					((CombatantCore) this.getCombatant()).getDynamicMenuManager().addHasGuildIcons();
					GuildMenuListener.requestedGuildNames.remove(player.getName());
				} else {
					player.sendMessage(ChatColor.RED + "A team with the name \"" + guildName + "\" already exists.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You are already in the guild: " + "\"" + this.getCombatant().getTeam().getTeamName() + 
						"\". You have to leave your guild before you start a new one.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for the Guild first!");
		}
	}
}
