package com.codari.arenacore.players.menu.menus.menustore.function;

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

public class SpawnableGroupSelection extends FunctionMenu {
	private SpawnableGroupSelection nextPage;
	private Kit kit;
	private String arenaObjectName;
	private BackIcon backIcon;
	
	public SpawnableGroupSelection(Combatant combatant, Kit kit, String arenaObjectName, BackIcon backIcon) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		for(String groupName : kit.getArenaBuilder().getRandomSpawnablesCopyMap().keySet()) {
			this.addSpawnableGroupIcon(combatant, kit, arenaObjectName, groupName, backIcon);
		}
		this.kit = kit;
		this.arenaObjectName = arenaObjectName;
		this.backIcon = backIcon;
		((CombatantCore)combatant).getKitManager().setSpawnableGroupSelectionMenu(combatant, this);
	}
	
	private SpawnableGroupSelection(Combatant combatant, Icon previous) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, this.backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
	}
	
	public void addSpawnableGroupIcon(Combatant combatant, Kit kit, String arenaObjectName, String groupName, BackIcon backIcon) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new SpawnableGroupIcon(combatant, new SlotSelection(combatant, kit, groupName, arenaObjectName, new BackIcon(combatant, this)), groupName));
		} else {
			if(this.nextPage != null) {
				this.nextPage.addSpawnableGroupIcon(combatant, kit, arenaObjectName, groupName, backIcon);
			} else {
				this.addNextPage(combatant);
				this.nextPage.addSpawnableGroupIcon(combatant, kit, arenaObjectName, groupName, backIcon);
			}
		}
	}
	
	private void addNextPage(Combatant combatant) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new SpawnableGroupSelection(combatant, prevIcon);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}	
	
	public Kit getKit() {
		return this.kit;
	}
	
	public String getArenaObjectName() {
		return this.arenaObjectName;
	}
	
	public BackIcon getBackIcon() {
		return this.backIcon;
	}
}
