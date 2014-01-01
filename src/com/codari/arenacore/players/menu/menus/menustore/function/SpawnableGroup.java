package com.codari.arenacore.players.menu.menus.menustore.function;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.CreateSpawnableGroupIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.EditSpawnableGroupIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroup extends FunctionMenu {

	public SpawnableGroup(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon);
	}

	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_THREE, new EditSpawnableGroupIcon(combatant, new SpawnableGroupEdit(combatant, kit, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_FOUR, new CreateSpawnableGroupIcon(combatant, new SpawnableGroupCreate(combatant, kit, new BackIcon(combatant, this))));
	}
}
