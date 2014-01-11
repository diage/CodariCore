package com.codari.arenacore.players.menu.menus.menustore.teams;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.players.KickPlayerIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.players.SetLeaderIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class PlayerOptions extends FunctionMenu {

	public PlayerOptions(Combatant combatant, Combatant teamMate, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, teamMate, backIcon);
	}

	private void addIcons(Combatant combatant, Combatant teamMate, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SetLeaderIcon(combatant, teamMate));
		super.setSlot(FunctionMenuSlot.A_TWO, new KickPlayerIcon(combatant, teamMate));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
