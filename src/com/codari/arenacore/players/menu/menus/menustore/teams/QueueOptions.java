package com.codari.arenacore.players.menu.menus.menustore.teams;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.queue.JoinQueueIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.queue.LeaveQueueIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class QueueOptions extends FunctionMenu {

	public QueueOptions(Combatant combatant, Team team, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, team, backIcon);
	}

	private void addIcons(Combatant combatant, Team team, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new JoinQueueIcon(combatant, new ArenaSelection(combatant, team, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.A_TWO, new LeaveQueueIcon(combatant, team));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
