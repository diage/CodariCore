package com.codari.arenacore.players.menu.menus.menustore.guilds;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.create.CreateGuildIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.create.SelectGuildNameIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class GuildCreate extends FunctionMenu{

	public GuildCreate(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SelectGuildNameIcon(combatant));
		super.setSlot(FunctionMenuSlot.A_TWO, new CreateGuildIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
