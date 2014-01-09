package com.codari.arenacore.players.menu.menus.menustore.teams;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.CheckPlayersIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.InvitePlayersIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.QueueIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.SelectPlayersIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class TeamEdit extends FunctionMenu {

	public TeamEdit(Combatant combatant, Team team, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, team, backIcon);
	}

	private void addIcons(Combatant combatant, Team team, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new CheckPlayersIcon(combatant, new PlayerSelection(combatant, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.A_TWO, new QueueIcon(combatant, new QueueOptions(combatant, team, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.B_ONE, new SelectPlayersIcon(combatant));
		super.setSlot(FunctionMenuSlot.B_TWO, new InvitePlayersIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
