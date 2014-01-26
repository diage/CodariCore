package com.codari.arenacore.players.menu.menus.menustore.guilds;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.guilds.Guild;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.options.AcceptGuildInviteIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.options.CreateGuildMenuIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.options.DeclineGuildInviteIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.options.GuildIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.options.LeaveGuildIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class InitialGuildOptions extends FunctionMenu {
	private Combatant combatant;
	private CreateGuildMenuIcon createGuildMenuIcon;
	private LeaveGuildIcon leaveGuildIcon;
	
	public InitialGuildOptions(Combatant combatant) {
		super(combatant);
		this.combatant = combatant;
		this.createGuildMenuIcon = new CreateGuildMenuIcon(this.combatant, new GuildCreate(this.combatant, new BackIcon(this.combatant, this)));
		this.leaveGuildIcon = new LeaveGuildIcon(this.combatant);
		this.addIcons();
		((CombatantCore) this.combatant).getDynamicMenuManager().setInitialGuildOptionsMenu(this);
	}

	public void setHasGuildIcons() {
		super.setSlot(FunctionMenuSlot.A_ONE, new GuildIcon(this.combatant, new GuildEdit(this.combatant, ((CombatantCore) this.combatant).getGuild(), new BackIcon(this.combatant, this)), ((CombatantCore) this.combatant).getGuild().getGuildName()));
		super.setSlot(FunctionMenuSlot.A_TWO, this.leaveGuildIcon);
	}
	
	@SuppressWarnings("deprecation")
	public void setNoGuildIcon() {
		if(super.hasSlot(FunctionMenuSlot.A_TWO)) {
			((CombatantCore) this.combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.A_TWO);
		} 
		super.setSlot(FunctionMenuSlot.A_ONE, this.createGuildMenuIcon);
		this.combatant.getPlayer().updateInventory();
	}
	
	public void addGuildInvitationIcons(Guild guild) {
		super.setSlot(FunctionMenuSlot.B_ONE, new AcceptGuildInviteIcon(this.combatant, guild));
		super.setSlot(FunctionMenuSlot.B_TWO, new DeclineGuildInviteIcon(this.combatant));
	}
	
	public void removeGuildInvitationIcons() {
		if(super.hasSlot(FunctionMenuSlot.B_ONE)) {
			((CombatantCore) this.combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.B_ONE);
		} 
		if(super.hasSlot(FunctionMenuSlot.B_TWO)) {
			((CombatantCore) this.combatant).getMenuManager().removeMenuSlot(FunctionMenuSlot.B_TWO);
		} 
	}
	
	private void addIcons() {
		if(((CombatantCore) this.combatant).getGuild() != null) {
			this.setHasGuildIcons();
		} else {
			this.setNoGuildIcon();
		}
		
		if(((CombatantCore) this.combatant).checkIfBeingInvitedToGuild()) {
			this.addGuildInvitationIcons(((CombatantCore) this.combatant).getInviteGuild());
		}
	}
}
