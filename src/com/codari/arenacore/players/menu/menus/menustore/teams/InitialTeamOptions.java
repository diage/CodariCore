package com.codari.arenacore.players.menu.menus.menustore.teams;

import org.bukkit.Bukkit;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.CreateTeamMenuIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.LeaveTeamIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.TeamIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class InitialTeamOptions extends FunctionMenu {
	
	public InitialTeamOptions(Combatant combatant) {
		super(combatant);
		this.addIcons(combatant);
	}
	
	public void setHasTeamIcons(Combatant combatant) {
		super.setSlot(FunctionMenuSlot.A_ONE, new TeamIcon(combatant, new TeamEdit(combatant, new BackIcon(combatant, this)), combatant.getTeam().getTeamName()));
		super.setSlot(FunctionMenuSlot.A_TWO, new LeaveTeamIcon(combatant, this));
	}
	
	public void setNoTeamIcon(Combatant combatant) {
		super.setSlot(FunctionMenuSlot.A_ONE, new CreateTeamMenuIcon(combatant, new TeamCreate(combatant, new BackIcon(combatant, this))));
		if(super.hasSlot(FunctionMenuSlot.A_TWO)) {
			Bukkit.broadcastMessage("Removing icon!"); //TODO
			((CombatantCore) combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.A_TWO);
		} else {
			Bukkit.broadcastMessage("No icon to remove!"); //TODO
		}
	}
	
	private void addIcons(Combatant combatant) {
		if(combatant.getTeam() != null) {
			this.setHasTeamIcons(combatant);
		} else {
			this.setNoTeamIcon(combatant);
		}
	}
}
