package com.codari.arenacore.players.menu.icons.iconstore.guilds.edit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.guilds.Guild;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.GuildMenuListener;

public class InvitePlayerIcon extends ExecutableIcon {

	public InvitePlayerIcon(Combatant combatant) {
		super(Material.GOLD_NUGGET, combatant, "Invite the player you selected.");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		Guild guild = ((CombatantCore) this.getCombatant()).getGuild();	
		if(GuildMenuListener.requestedSelectPlayerNames.containsKey(player.getName())) {
			String invitedPlayerName = GuildMenuListener.requestedSelectPlayerNames.get(player.getName());
			Player invitedPlayer = Bukkit.getPlayer(invitedPlayerName);
			if(invitedPlayer != null) {
				CombatantCore invitedCombatant = (CombatantCore) Codari.getArenaManager().getCombatant(invitedPlayer);
				if(invitedCombatant.getGuild() == null) {
					if(!invitedCombatant.checkIfBeingInvitedToGuild()) {
						player.sendMessage(ChatColor.GREEN + "You have invited " + invitedPlayerName + " to your guild.");
						invitedCombatant.getDynamicMenuManager().addGuildInvitationIcons(guild);
						invitedCombatant.setBeingInvitedToGuild(guild);
						invitedPlayer.sendMessage(ChatColor.GREEN + "You have been invited to the Guild \"" + guild.getGuildName() + "\". "
								+ "Open up your Guild Menu to accept or decline.");
					} else {
						player.sendMessage(ChatColor.RED + invitedPlayerName + " is already being invited to a Guild!");
					}
				} else {
					player.sendMessage(ChatColor.RED + invitedPlayerName + " is already on a Guild!");
				}
			} else {
				player.sendMessage(ChatColor.RED + invitedPlayerName + " is not a valid Player name!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for a Player first!");
		}
	}
	
}
