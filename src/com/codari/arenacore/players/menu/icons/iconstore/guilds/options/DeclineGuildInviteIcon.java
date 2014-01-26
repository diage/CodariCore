package com.codari.arenacore.players.menu.icons.iconstore.guilds.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class DeclineGuildInviteIcon extends ExecutableIcon {

	public DeclineGuildInviteIcon(Combatant combatant) {
		super(Material.SPRUCE_WOOD_STAIRS, combatant, "Decline Guild Invite");
	}

	@Override
	public void click() {
		((CombatantCore) this.getCombatant()).getDynamicMenuManager().removeGuildInvitationIcons();
		((CombatantCore) this.getCombatant()).setBeingInvitedToGuild(null);
	}

}
