package com.codari.arenacore.players.menu.icons.iconstore.teams.create;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.SaveTeamIconListener;
import com.codari.arenacore.players.menu.menus.menustore.teams.InitialTeamOptions;
import com.codari.arenacore.players.teams.TeamBuilder;

public class CreateTeamIcon extends ExecutableIcon {
	private InitialTeamOptions initialTeamOptions;
	
	public CreateTeamIcon(Combatant combatant, InitialTeamOptions initialTeamOptions) {
		super(Material.EMERALD_BLOCK, combatant, "Create Team");
		this.initialTeamOptions = initialTeamOptions;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(SaveTeamIconListener.requestedTeamNames.containsKey(player.getName())) {
			if(this.getCombatant().getTeam() == null) {
				String teamName = SaveTeamIconListener.requestedTeamNames.get(player.getName());
				if(!Codari.getTeamManager().containsTeam(teamName)) {
					TeamBuilder.createNewTeam(this.getCombatant().getPlayer(), teamName);
					player.sendMessage(ChatColor.GREEN + "You have created a new team named " + "\"" + teamName + "\"");
					this.initialTeamOptions.setHasTeamIcons();
				} else {
					player.sendMessage(ChatColor.RED + "A team with the name \"" + teamName + "\" already exists");
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
