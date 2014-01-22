package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.api5.CodariI;
import com.codari.apicore.CodariCore;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.rolesettings.ArenaRoleIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class RoleAddition extends FunctionMenu {
	private RoleAddition nextPage;
	private BackIcon backIcon;
	
	public RoleAddition(Combatant combatant) {
		super(combatant);
		for(String roleName : ((CodariCore) CodariI.INSTANCE).getRoleManager().getRoles()) {
			this.addRoleIcon(combatant, roleName);	
		}
	}
	
	private RoleAddition(Combatant combatant, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}

	
	public void addRoleIcon(Combatant combatant, String roleName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new ArenaRoleIcon(combatant, roleName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addRoleIcon(combatant, roleName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addRoleIcon(combatant, roleName);
			}
		}
	}
	
	public void setBackIcon(BackIcon backIcon) {
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		if(this.nextPage != null) {
			this.nextPage.setBackIcon(backIcon);
		}
	}

	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new RoleAddition(combatant, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
