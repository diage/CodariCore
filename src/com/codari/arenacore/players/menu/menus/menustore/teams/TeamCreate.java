package com.codari.arenacore.players.menu.menus.menustore.teams;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.create.CreateTeamIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.create.SelectTeamNameIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class TeamCreate extends FunctionMenu {
	
	public TeamCreate(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SelectTeamNameIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new CreateTeamIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
