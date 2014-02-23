package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.SetSlotIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.role.ArenaRoleDataIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class RoleSelectionObjectSettings extends FunctionMenu {
	private RoleSelectionObjectSettings nextPage;
	private String arenaObjectName;
	private BackIcon backIcon;

	public RoleSelectionObjectSettings(Combatant combatant, Kit kit, String arenaObjectName, BackIcon backIcon) {
		super(combatant);
		this.arenaObjectName = arenaObjectName;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_THREE, new SetSlotIcon(combatant, new PersistentSlotSelection(combatant, arenaObjectName, new BackIcon(combatant, this))));
		if(((ArenaManagerCore) Codari.getArenaManager()).hasAnExistingRole(kit.getName())) {
			for(String roleName : ((ArenaManagerCore) Codari.getArenaManager()).getExistingRoleNames(kit.getName())) {
				this.addArenaRoleIcon(combatant, roleName);	
			}
		}
		((CombatantCore)combatant).getDynamicMenuManager().setRoleSelectionObjectSettingsMenu(kit, this);
	}

	private RoleSelectionObjectSettings(Combatant combatant, String arenaObjectName, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.arenaObjectName = arenaObjectName;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_THREE, new SetSlotIcon(combatant, new PersistentSlotSelection(combatant, arenaObjectName, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}


	public void addArenaRoleIcon(Combatant combatant, String roleName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new ArenaRoleDataIcon(combatant, new RoleDataSettings(combatant, roleName, new BackIcon(combatant, this)), roleName));
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
		this.nextPage = new RoleSelectionObjectSettings(combatant, this.arenaObjectName, prevIcon, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
