package com.codari.arenacore.players.menu.menus.menustore.teams;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.players.PlayerIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class PlayerSelection extends FunctionMenu {
	private PlayerSelection nextPage;
	private BackIcon backIcon;
	
	public PlayerSelection(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		if(combatant.getTeam() != null && combatant.getTeam().getTeamSize() > 1) {
			for(Combatant teamMate: combatant.getTeam().getTeamMates(combatant)) {
				this.addPlayerIcon(combatant, teamMate.getPlayer().getName());
			}
		}
		((CombatantCore)combatant).getDynamicMenuManager().setPlayerSelectionMenu(this);
	}
	
	private PlayerSelection(Combatant combatant, BackIcon backIcon, Icon previous) {
		super(combatant);
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}	
	
	public void addPlayerIcon(Combatant combatant, String playerName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new PlayerIcon(combatant, new PlayerOptions(combatant, Codari.getArenaManager().getCombatant(playerName), new BackIcon(combatant, this)), playerName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addPlayerIcon(combatant, playerName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addPlayerIcon(combatant, playerName);
			}
		}
	}	
	
	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new PlayerSelection(combatant, this.backIcon, prevIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
