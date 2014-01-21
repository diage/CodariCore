package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.rolesettings.AddRoleIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.rolesettings.RoleOptionsIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class RoleSettings extends FunctionMenu {
	private RoleSettings nextPage;
	private Kit kit;
	private BackIcon backIcon;
	
	public RoleSettings(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.kit = kit;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_THREE, new AddRoleIcon(combatant, new RoleAddition(combatant, this.kit, new BackIcon(combatant, this))));
		if(((ArenaManagerCore) Codari.getArenaManager()).hasAnExistingRole(this.kit.getName())) {
			for(String roleName : ((ArenaManagerCore) Codari.getArenaManager()).getExistingRoleNames(this.kit.getName())) {
				this.addArenaRoleIcon(combatant, roleName);
			}
		}
		((CombatantCore)combatant).getDynamicMenuManager().setRoleSettingsMenu(this.kit, this);
	}
	
	private RoleSettings(Combatant combatant, Kit kit, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.kit = kit;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
		super.setSlot(FunctionMenuSlot.C_THREE, new AddRoleIcon(combatant, new RoleAddition(combatant, this.kit, new BackIcon(combatant, this))));
	}

	
	public void addArenaRoleIcon(Combatant combatant, String roleName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new RoleOptionsIcon(combatant, new RoleOptions(combatant, new BackIcon(combatant, this)), roleName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addArenaRoleIcon(combatant, roleName);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addArenaRoleIcon(combatant, roleName);
			}
		}
	}

	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new RoleSettings(combatant, this.kit, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}		
}
