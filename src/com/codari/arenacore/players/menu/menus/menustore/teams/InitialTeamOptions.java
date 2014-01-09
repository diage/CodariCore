package com.codari.arenacore.players.menu.menus.menustore.teams;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.CreateTeamMenuIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.LeaveTeamIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.options.TeamIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class InitialTeamOptions extends FunctionMenu {
	private TeamIcon teamIcon;
	private LeaveTeamIcon leaveTeamIcon;
	private CreateTeamMenuIcon createTeamMenuIcon;
	
	public InitialTeamOptions(Combatant combatant) {
		super(combatant);
		this.teamIcon = new TeamIcon(combatant, new TeamEdit(combatant, new BackIcon(combatant, this)), combatant.getTeam().getTeamName());
		this.leaveTeamIcon = new LeaveTeamIcon(combatant, this);
		this.createTeamMenuIcon = new CreateTeamMenuIcon(combatant, new TeamCreate(combatant, this, new BackIcon(combatant, this)));
		this.addIcons(combatant);
	}
	
	public void setHasTeamIcons() {
		super.setSlot(FunctionMenuSlot.A_ONE, this.teamIcon);
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
	
	private void addIcons(Combatant combatant) {
		if(combatant.getTeam() != null) {
			this.setHasTeamIcons();
		} else {
			this.setNoTeamIcon(combatant);
		}
	}
}
