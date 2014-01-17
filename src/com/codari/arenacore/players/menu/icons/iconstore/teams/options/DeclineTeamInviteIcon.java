package com.codari.arenacore.players.menu.icons.iconstore.teams.options;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class DeclineTeamInviteIcon extends ExecutableIcon  {
	
	public DeclineTeamInviteIcon(Combatant combatant) {
		super(Material.SPRUCE_WOOD_STAIRS, combatant, "Decline Team Invite");
	}

	@Override
	public void click() {
		((CombatantCore) this.getCombatant()).getDynamicMenuManager().removeTeamInvitationIcons();
		((CombatantCore) this.getCombatant()).setBeingInvitedToTeam(false);	
	}
	
}
