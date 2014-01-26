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
	private Combatant combatant;
	private CreateTeamMenuIcon createTeamMenuIcon;
	private LeaveTeamIcon leaveTeamIcon;
	
	public InitialTeamOptions(Combatant combatant) {
		super(combatant);
		this.combatant = combatant;
		this.createTeamMenuIcon = new CreateTeamMenuIcon(this.combatant, new TeamCreate(this.combatant, new BackIcon(this.combatant, this)));
		this.leaveTeamIcon = new LeaveTeamIcon(this.combatant);
		this.addIcons();
		((CombatantCore) this.combatant).getDynamicMenuManager().setInitialTeamOptionsMenu(this);
	}
	
	public void setHasTeamIcons() {
		super.setSlot(FunctionMenuSlot.A_ONE, new TeamIcon(this.combatant, new TeamEdit(this.combatant, this.combatant.getTeam(), new BackIcon(this.combatant, this)), this.combatant.getTeam().getTeamName()));
		super.setSlot(FunctionMenuSlot.A_TWO, this.leaveTeamIcon);
	}
	
	@SuppressWarnings("deprecation")
	public void setNoTeamIcon() {
		if(super.hasSlot(FunctionMenuSlot.A_TWO)) {
			((CombatantCore) this.combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.A_TWO);
		} 
		super.setSlot(FunctionMenuSlot.A_ONE, this.createTeamMenuIcon);
		this.combatant.getPlayer().updateInventory();
	}
	
	public void addTeamInvitationIcons(Team team) {
		super.setSlot(FunctionMenuSlot.B_ONE, new AcceptTeamInviteIcon(this.combatant, team));
		super.setSlot(FunctionMenuSlot.B_TWO, new DeclineTeamInviteIcon(this.combatant));
	}
	
	public void removeTeamInvitationIcons() {
		if(super.hasSlot(FunctionMenuSlot.B_ONE)) {
			((CombatantCore) this.combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.B_ONE);
		} 
		if(super.hasSlot(FunctionMenuSlot.B_TWO)) {
			((CombatantCore) this.combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.B_TWO);
		} 
	}
	
	private void addIcons() {
		if(this.combatant.getTeam() != null) {
			this.setHasTeamIcons();
		} else {
			this.setNoTeamIcon();
		}
		
		if(((CombatantCore) this.combatant).checkIfBeingInvitedToTeam()) {
			this.addTeamInvitationIcons(((CombatantCore) this.combatant).getInviteTeam());	
		}
	}
}
