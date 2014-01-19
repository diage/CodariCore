package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.ResetRandomRepeatIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.SaveIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatTicksIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroupRepeatSet extends FunctionMenu {
	private UpdateRandomRepeatMinutesIcon randomRepeatM;
	private UpdateRandomRepeatSecondsIcon randomRepeatS;
	private UpdateRandomRepeatTicksIcon randomRepeatT;
	
	public SpawnableGroupRepeatSet(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.randomRepeatM = new UpdateRandomRepeatMinutesIcon(combatant);
		this.randomRepeatS = new UpdateRandomRepeatSecondsIcon(combatant);
		this.randomRepeatT = new UpdateRandomRepeatTicksIcon(combatant);
		this.addIcons(combatant, kit, backIcon);
	}
	
	public void clearLore() {
		this.randomRepeatM.clear();
		this.randomRepeatS.clear();
		this.randomRepeatT.clear();
	}
	
	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, this.randomRepeatM);
		super.setSlot(FunctionMenuSlot.A_TWO, this.randomRepeatS);
		super.setSlot(FunctionMenuSlot.A_THREE, this.randomRepeatT);
		super.setSlot(FunctionMenuSlot.B_ONE, new ResetRandomRepeatIcon(combatant, this));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);	
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveIcon(combatant, kit));
	}	

}
