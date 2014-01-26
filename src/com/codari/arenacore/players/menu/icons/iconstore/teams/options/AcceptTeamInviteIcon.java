package com.codari.arenacore.players.menu.icons.iconstore.teams.options;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public class AcceptTeamInviteIcon extends ExecutableIcon {
	private TeamCore team;
	
	public AcceptTeamInviteIcon(Combatant combatant, Team team) {
		super(Material.ENCHANTMENT_TABLE, combatant, "Accept Team Invite");
		this.team = (TeamCore) team;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(this.team.getArena() == null) {
			if(!this.team.checkIfInQueue()) {
				player.sendMessage(ChatColor.GREEN + "You have joined \"" + this.team.getTeamName() + "\".");
				for(Player teamMatePlayer : this.team.getPlayers()) {
					teamMatePlayer.sendMessage(ChatColor.BLUE + player.getName() + " has joined your team!");
				}
				TeamBuilder.addPlayer(team, this.getCombatant());
				((CombatantCore) this.getCombatant()).getDynamicMenuManager().removeTeamInvitationIcons();
				((CombatantCore) this.getCombatant()).setBeingInvitedToTeam(null);
			} else {
				player.sendMessage(ChatColor.RED + "You can't join the Team because they are currently in a queue!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You can't join the Team because they are currently in an arena!");
		}
	}

}
