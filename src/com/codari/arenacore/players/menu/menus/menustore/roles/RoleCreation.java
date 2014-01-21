package com.codari.arenacore.players.menu.menus.menustore.roles;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.roles.creation.SelectRoleName;
import com.codari.arenacore.players.menu.icons.iconstore.roles.creation.SkillTypeSelectionIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class RoleCreation extends FunctionMenu {

	public RoleCreation(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}
	
	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SelectRoleName(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new SkillTypeSelectionIcon(combatant, new SkillActivationTypes(combatant, new BackIcon(combatant, this))));
		
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}

}
