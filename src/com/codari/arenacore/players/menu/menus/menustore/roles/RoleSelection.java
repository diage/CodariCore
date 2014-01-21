package com.codari.arenacore.players.menu.menus.menustore.roles;

import com.codari.api5.CodariI;
import com.codari.apicore.CodariCore;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.roles.selection.NewRoleIcon;
import com.codari.arenacore.players.menu.icons.iconstore.roles.selection.RoleIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class RoleSelection extends FunctionMenu {
	private RoleSelection nextPage;
	
	/**
	 *  Construct the first page for a Role Selection Menu.
	 *  
	 *  @param Combatant reference
	 *  
	 *   */
	public RoleSelection(Combatant combatant) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_THREE, new NewRoleIcon(combatant, new RoleCreation(combatant, new BackIcon(combatant, this))));
		for(String roleName : (((CodariCore) CodariI.INSTANCE).getRoleManager()).getRoles()) {
			this.addRoleIcon(combatant, roleName);
		}
		((CombatantCore)combatant).getDynamicMenuManager().setRoleSelectionMenu(this);
	}
	
	/* This will construct any further needed pages for Role Selection. */
	private RoleSelection(Combatant combatant, Icon previous) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, previous);
		super.setSlot(FunctionMenuSlot.C_THREE, new NewRoleIcon(combatant, new RoleCreation(combatant, new BackIcon(combatant, this))));
	}	
	
	public void addRoleIcon(Combatant combatant, String roleName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new RoleIcon(combatant, new RoleOptions(combatant, ((CodariCore) CodariI.INSTANCE).getRoleManager().getRole(roleName), new BackIcon(combatant, this)), roleName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addRoleIcon(combatant, roleName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addRoleIcon(combatant, roleName);
			}
		}
	}	
	
	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new RoleSelection(combatant, prevIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
