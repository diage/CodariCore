package com.codari.arenacore.players.menu.menus.menustore.kitbuilders;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationInfiniteIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.SaveMatchDurationIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class MatchDurationSettings extends FunctionMenu {

	public MatchDurationSettings(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new MatchDurationMinutesIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new MatchDurationSecondsIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_THREE, new MatchDurationTicksIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_FIVE, new SaveMatchDurationIcon(combatant));
		super.setSlot(FunctionMenuSlot.B_ONE, new MatchDurationInfiniteIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
