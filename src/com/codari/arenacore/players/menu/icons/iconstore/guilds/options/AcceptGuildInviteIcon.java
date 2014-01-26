package com.codari.arenacore.players.menu.icons.iconstore.guilds.options;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.guilds.Guild;
import com.codari.arena5.players.guilds.GuildRanking;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.guilds.GuildBuilder;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class AcceptGuildInviteIcon extends ExecutableIcon {
	private Guild guild;
	
	public AcceptGuildInviteIcon(Combatant combatant, Guild guild) {
		super(Material.ENCHANTMENT_TABLE, combatant, "Accept Guild Invite");
		this.guild = guild;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		player.sendMessage(ChatColor.GREEN + "You have joined \"" + this.guild.getGuildName() + "\".");
		for(Combatant teamMateCombatant : this.guild.getGuildMembers()) {
			teamMateCombatant.getPlayer().sendMessage(ChatColor.BLUE + player.getName() + " has joined your team!");
		}
		GuildBuilder.addGuildMember(this.getCombatant(), guild, GuildRanking.MEMBER);
		((CombatantCore) this.getCombatant()).getDynamicMenuManager().removeGuildInvitationIcons();
		((CombatantCore) this.getCombatant()).setBeingInvitedToGuild(null);
	}

}
