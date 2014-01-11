package com.codari.arenacore.players.menu.icons.iconstore.teams.options;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.menus.menustore.teams.InitialTeamOptions;

public class DeclineTeamInviteIcon extends ExecutableIcon  {
	private Team team;
	private InitialTeamOptions initialTeamOptions;
	
	public DeclineTeamInviteIcon(Combatant combatant, Team team, InitialTeamOptions initialTeamOptions) {
		super(Material.SPRUCE_WOOD_STAIRS, combatant, "Decline Team Invite");
		this.team = team;
		this.initialTeamOptions = initialTeamOptions;
	}

	@Override
	public void click() {
		for(Player teamMatePlayer : this.team.getPlayers()) {
			teamMatePlayer.sendMessage(ChatColor.RED + this.getCombatant().getPlayer().getName() + " has declined your team!");
		}
		this.initialTeamOptions.removeInvitationIcons(this.getCombatant());
		((CombatantCore) this.getCombatant()).setBeingInvitedToTeam(false);	
	}
	
}
