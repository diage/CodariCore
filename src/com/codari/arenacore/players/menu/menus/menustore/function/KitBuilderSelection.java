package com.codari.arenacore.players.menu.menus.menustore.function;

import java.util.Iterator;
import java.util.Set;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.builderselection.KitBuilderIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitBuilderSelection extends FunctionMenu {

	public KitBuilderSelection(Combatant combatant, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, backIcon);
	}
	
	private KitBuilderSelection(Combatant combatant, Icon previous, Set<String> kitBuilderNames) {
		super(combatant);
		this.addIcons(combatant, previous, kitBuilderNames);
	}

	private void addIcons(Combatant combatant, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		Set<String> kitBuilderNames = ((CombatantCore)combatant).getKitManager().getKitBuilders().keySet();
		
		int i = 0;
		Iterator<String> iterator = kitBuilderNames.iterator();
		while(i < 10 && iterator.hasNext()) {
			String kitBuilderName = iterator.next();
			this.addKitBuilderIcon(combatant, kitBuilderName);
			iterator.remove();
			i++;
		}
		
		if(kitBuilderNames.size() > 0) {
			Icon prevIcon = new PreviousIcon(combatant, this);
			Icon nextIcon = new NextIcon(combatant, new KitBuilderSelection(combatant, prevIcon, kitBuilderNames));
			this.addNextIcon(nextIcon);
		}
	}
	
	private void addIcons(Combatant combatant, Icon previous, Set<String> kitBuilderNames) {
		super.setSlot(FunctionMenuSlot.C_ONE, new BackIcon(combatant, new KitCreation(combatant, new BackIcon(combatant, this))));
		this.addPreviousIcon(previous);
		
		int i = 0;
		Iterator<String> iterator = kitBuilderNames.iterator();
		while(i < 10 && iterator.hasNext()) {
			String kitBuilderName = iterator.next();
			this.addKitBuilderIcon(combatant, kitBuilderName);
			iterator.remove();
			i++;
		}
		
		if(kitBuilderNames.size() > 0) {
			Icon prevIcon = new PreviousIcon(combatant, this);
			Icon nextIcon = new NextIcon(combatant, new KitBuilderSelection(combatant, prevIcon, kitBuilderNames));
			this.addNextIcon(nextIcon);
		}		
	}
	
	private void addKitBuilderIcon(Combatant combatant, String kitBuilderName) {
		super.setSlot(super.getNextAvailableSlot(), new KitBuilderIcon(combatant, kitBuilderName));
	}
	
	private void addNextIcon(Icon icon) {
		super.setSlot(FunctionMenuSlot.C_FIVE, icon);
	}
	
	private void addPreviousIcon(Icon icon) {
		super.setSlot(FunctionMenuSlot.C_TWO, icon);
	}	
}
