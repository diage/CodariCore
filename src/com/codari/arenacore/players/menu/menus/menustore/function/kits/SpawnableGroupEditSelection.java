package com.codari.arenacore.players.menu.menus.menustore.function.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.SpawnableGroupIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroupEditSelection extends FunctionMenu {
	private SpawnableGroupEditSelection nextPage;
	private Kit kit;
	private BackIcon backIcon;
	
	public SpawnableGroupEditSelection(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		for(String groupName : kit.getArenaBuilder().getRandomSpawnablesCopyMap().keySet()) {
			this.addSpawnableGroupIcon(combatant, groupName);
		}
		this.kit = kit;
		this.backIcon = backIcon;
		((CombatantCore)combatant).getKitManager().setSpawnableGroupEditSelectionMenu(combatant, this);
	}
	
	private SpawnableGroupEditSelection(Combatant combatant, Kit kit, Icon previous, BackIcon backIcon) {
		super(combatant);
		this.kit = kit;
		this.backIcon = backIcon;
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}
	
	public void addSpawnableGroupIcon(Combatant combatant, String groupName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new SpawnableGroupIcon(combatant, new SpawnableGroupEdit(combatant, this.kit, new BackIcon(combatant, this)), groupName));
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
		this.nextPage = new SpawnableGroupEditSelection(combatant, kit, prevIcon, backIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
}
