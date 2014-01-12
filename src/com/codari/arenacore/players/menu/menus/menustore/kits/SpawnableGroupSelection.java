package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.SpawnableGroupIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroupSelection extends FunctionMenu {
	private SpawnableGroupSelection nextPage;
	private Kit kit;
	private BackIcon backIcon;
	
	public SpawnableGroupSelection(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		for(String groupName : ((ArenaManagerCore) Codari.getArenaManager()).getArenaBuilder(kit.getName()).getRandomSpawnablesCopyMap().keySet()) {
			this.addSpawnableGroupIcon(combatant, groupName);
		}
		this.kit = kit;
		this.backIcon = backIcon;
		((CombatantCore)combatant).getDynamicMenuManager().setSpawnableGroupSelectionMenu(this);
	}
	
	private SpawnableGroupSelection(Combatant combatant, Kit kit, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.kit = kit;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}
	
	public void addSpawnableGroupIcon(Combatant combatant, String groupName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new SpawnableGroupIcon(combatant, new SlotSelection(combatant, this.kit, groupName, new BackIcon(combatant, this)), groupName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addSpawnableGroupIcon(combatant, groupName);
			} else {
				this.addNextPage(combatant, this.kit, this.backIcon);
				this.nextPage.addSpawnableGroupIcon(combatant, groupName);
			}
		}
	}
	
	private void addNextPage(Combatant combatant, Kit kit, BackIcon backIcon) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new SpawnableGroupSelection(combatant, kit, prevIcon, backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
