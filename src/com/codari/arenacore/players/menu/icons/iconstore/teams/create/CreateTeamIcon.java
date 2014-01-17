package com.codari.arenacore.players.menu.icons.iconstore.teams.create;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.TeamMenuListener;
import com.codari.arenacore.players.teams.TeamBuilder;

public class CreateTeamIcon extends ExecutableIcon {	
	public CreateTeamIcon(Combatant combatant) {
		super(Material.EMERALD_BLOCK, combatant, "Create Team");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(TeamMenuListener.requestedTeamNames.containsKey(player.getName())) {
			if(this.getCombatant().getTeam() == null) {
				String teamName = TeamMenuListener.requestedTeamNames.get(player.getName());
				if(!Codari.getTeamManager().containsTeam(teamName)) {
					TeamBuilder.createNewTeam(this.getCombatant().getPlayer(), teamName);
					player.sendMessage(ChatColor.GREEN + "You have created a new team named " + "\"" + teamName + "\"");
					((CombatantCore) this.getCombatant()).getDynamicMenuManager().addHasTeamIcons();
					TeamMenuListener.requestedTeamNames.remove(player.getName());
				} else {
					player.sendMessage(ChatColor.RED + "A team with the name \"" + teamName + "\" already exists.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You are already on the team: " + "\"" + this.getCombatant().getTeam().getTeamName() + 
						"\". You have to leave your team before you start a new one.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for the Team first!");
		}
	}

}
