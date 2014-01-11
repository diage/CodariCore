package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.AddTimedActionsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.AddWinConditionsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SelectKitBuilderNameIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SetMatchDurationIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SetNumberOfTeamsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SetTeamSizeIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SubmitKitBuilderIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitBuilderCreation extends FunctionMenu {

	public KitBuilderCreation(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SelectKitBuilderNameIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new SetMatchDurationIcon(combatant, new MatchDurationSettings(combatant, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.A_THREE, new SetTeamSizeIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_FOUR, new SetNumberOfTeamsIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, new AddWinConditionsIcon(combatant, new WinConditionSettings(combatant, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_THREE, new AddTimedActionsIcon(combatant, new TimedActionSettings(combatant, new BackIcon(combatant, this))));		
		super.setSlot(FunctionMenuSlot.C_FIVE, new SubmitKitBuilderIcon(combatant));
	} 
}
