package com.codari.arenacore.players.menu.icons.iconstore.teams.edit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.SelectPlayerInviteIconListener;

public class InvitePlayersIcon extends ExecutableIcon {

	public InvitePlayersIcon(Combatant combatant) {
		super(Material.GOLD_NUGGET, combatant, "Invite all the players you selected.");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		Team team = this.getCombatant().getTeam();
		if(SelectPlayerInviteIconListener.requestedSelectPlayerNames.containsKey(player.getName())) {
			String invitedPlayerName = SelectPlayerInviteIconListener.requestedSelectPlayerNames.get(player.getName());
			Player invitedPlayer = Bukkit.getPlayer(invitedPlayerName);
			if(invitedPlayer != null) {
				CombatantCore invitedCombatant = (CombatantCore) Codari.getArenaManager().getCombatant(invitedPlayer);
				if(invitedCombatant.getTeam() != null) {
					if(!invitedCombatant.checkIfBeingInvitedToTeam()) {
						player.sendMessage(ChatColor.GREEN + "You have invited " + invitedPlayerName + " to your team");
						invitedCombatant.getDynamicMenuManager().addInvitationIcons(team);
						invitedPlayer.sendMessage(ChatColor.GREEN + "You have been invited to the team \"" + team.getTeamName() + "\". "
								+ "Open up your team menu to accept or decline.");
					} else {
						player.sendMessage(ChatColor.RED + invitedPlayerName + " is already being invited to a Team!");
					}
				} else {
					player.sendMessage(ChatColor.RED + invitedPlayerName + " is already on a Team!");
				}
			} else {
				player.sendMessage(ChatColor.RED + invitedPlayerName + " is not a valid Player name!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for a Player first!");
		}
	}
}
