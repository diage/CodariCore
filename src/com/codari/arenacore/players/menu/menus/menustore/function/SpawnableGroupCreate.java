package com.codari.arenacore.players.menu.menus.menustore.function;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.SelectNameIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.SetDelayTimeIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.SetRepeatTimeIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroupCreate extends FunctionMenu {

	public SpawnableGroupCreate(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon);
	}
	
	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, new SelectNameIcon(combatant, kit));
		super.setSlot(FunctionMenuSlot.A_TWO, new SetDelayTimeIcon(combatant, new SpawnableGroupDelaySet(combatant, kit, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.A_THREE, new SetRepeatTimeIcon(combatant, new SpawnableGroupRepeatSet(combatant, kit, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
	}
}
