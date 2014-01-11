package com.codari.arenacore.players.menu.menus.menustore.teams;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.AcceptTeamInviteIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.CreateTeamMenuIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.DeclineTeamInviteIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.LeaveTeamIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.TeamIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class InitialTeamOptions extends FunctionMenu {
	private LeaveTeamIcon leaveTeamIcon;
	private CreateTeamMenuIcon createTeamMenuIcon;
	
	public InitialTeamOptions(Combatant combatant) {
		super(combatant);
		this.leaveTeamIcon = new LeaveTeamIcon(combatant, this);
		this.createTeamMenuIcon = new CreateTeamMenuIcon(combatant, new TeamCreate(combatant, this, new BackIcon(combatant, this)));
		this.addIcons(combatant);
		((CombatantCore) combatant).getDynamicMenuManager().setInitialTeamOptionsMenu(this);
	}
	
	public void setHasTeamIcons(Combatant combatant) {
		super.setSlot(FunctionMenuSlot.A_ONE, new TeamIcon(combatant, new TeamEdit(combatant, combatant.getTeam(), new BackIcon(combatant, this)), combatant.getTeam().getTeamName()));
		super.setSlot(FunctionMenuSlot.A_TWO, this.leaveTeamIcon);
	}
	
	@SuppressWarnings("deprecation")
	public void setNoTeamIcon(Combatant combatant) {
		if(super.hasSlot(FunctionMenuSlot.A_TWO)) {
			((CombatantCore) combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.A_TWO);
		} 
		super.setSlot(FunctionMenuSlot.A_ONE, this.createTeamMenuIcon);
		combatant.getPlayer().updateInventory();
	}
	
	public void addInvitationIcons(Combatant combatant, Team team) {
		super.setSlot(FunctionMenuSlot.B_ONE, new AcceptTeamInviteIcon(combatant, team, this));
		super.setSlot(FunctionMenuSlot.B_TWO, new DeclineTeamInviteIcon(combatant, team, this));
	}
	
	public void removeInvitationIcons(Combatant combatant) {
		if(super.hasSlot(FunctionMenuSlot.B_ONE)) {
			((CombatantCore) combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.B_ONE);
		} 
		if(super.hasSlot(FunctionMenuSlot.B_TWO)) {
			((CombatantCore) combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.B_TWO);
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
