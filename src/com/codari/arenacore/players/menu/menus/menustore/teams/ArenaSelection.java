package com.codari.arenacore.players.menu.menus.menustore.teams;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.queue.choose.ArenaIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class ArenaSelection extends FunctionMenu {
	private ArenaSelection nextPage;
	private Team team;
	private BackIcon backIcon;
	
	public ArenaSelection(Combatant combatant, Team team, BackIcon backIcon) {
		super(combatant);
		this.team = team;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		for(String arenaName : ((ArenaManagerCore) Codari.getArenaManager()).getArenaNames()) {
			this.addArenaIcon(combatant, arenaName);
		}
		((CombatantCore)combatant).getDynamicMenuManager().setArenaSelectionMenu(this);
	}
	
	private ArenaSelection(Combatant combatant, Team team, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.team = team;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}

	
	public void addArenaIcon(Combatant combatant, String arenaName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new ArenaIcon(combatant, arenaName, this.team));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addArenaIcon(combatant, arenaName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addArenaIcon(combatant, arenaName);
			}
		}
	}

	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new ArenaSelection(combatant, this.team, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
