package com.codari.arenacore.players.menu.menus.menustore.kits;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.ResetRandomDelayIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.SaveIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelayTicksIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroupDelaySet extends FunctionMenu {
	private UpdateRandomDelayMinutesIcon randomDelayM;
	private UpdateRandomDelaySecondsIcon randomDelayS;
	private UpdateRandomDelayTicksIcon randomDelayT;

	public SpawnableGroupDelaySet(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon);
		this.randomDelayM = new UpdateRandomDelayMinutesIcon(combatant);
		this.randomDelayS = new UpdateRandomDelaySecondsIcon(combatant);
		this.randomDelayT = new UpdateRandomDelayTicksIcon(combatant);
	}
	
	public void clearLore() {
		this.randomDelayM.clear();
		this.randomDelayS.clear();
		this.randomDelayT.clear();
	}
	
	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.A_ONE, this.randomDelayM);
		super.setSlot(FunctionMenuSlot.A_TWO, this.randomDelayS);
		super.setSlot(FunctionMenuSlot.A_THREE, this.randomDelayT);
		super.setSlot(FunctionMenuSlot.B_ONE, new ResetRandomDelayIcon(combatant, this));
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);	
		super.setSlot(FunctionMenuSlot.C_FIVE, new SaveIcon(combatant, kit));
	}
}
