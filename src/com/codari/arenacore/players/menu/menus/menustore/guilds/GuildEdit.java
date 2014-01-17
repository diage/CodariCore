package com.codari.arenacore.players.menu.menus.menustore.guilds;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.guilds.Guild;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.edit.InvitePlayerIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.edit.SelectPlayerIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class GuildEdit extends FunctionMenu {

	public GuildEdit(Combatant combatant, Guild guild, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, guild, backIcon);
	}

	private void addIcons(Combatant combatant, Guild guild, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.B_ONE, new SelectPlayerIcon(combatant));
		super.setSlot(FunctionMenuSlot.B_TWO, new InvitePlayerIcon(combatant));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
