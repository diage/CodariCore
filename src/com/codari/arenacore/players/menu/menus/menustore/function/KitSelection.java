package com.codari.arenacore.players.menu.menus.menustore.function;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.KitIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.NewKitIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitSelection extends FunctionMenu {
	/**
	 *  Construct the first page for a Kit Selection Menu.
	 *  
	 *  @param Combatant reference
	 *  
	 *   */
	public KitSelection(Combatant combatant) {
		super(combatant);
		this.addIcons(combatant);
		Bukkit.broadcastMessage(ChatColor.BLUE + "First page!");	//TODO
	}

	/* This will construct any further needed pages for Kit Selection. */
	private KitSelection(Combatant combatant, Icon previous, Set<String> kitNames) {
		super(combatant);
		this.addIcons(combatant, previous, kitNames);
	}

	private void addIcons(Combatant combatant) {
		super.setSlot(FunctionMenuSlot.C_THREE, new NewKitIcon(combatant, new KitCreation(combatant)));
		Set<String> kitNames = ((CombatantCore)combatant).getKitManager().getKits().keySet();
		
		int i = 0;
		Iterator<String> iterator = kitNames.iterator();
		while (i < 10 && iterator.hasNext()) {
			String kitName = iterator.next();
			this.addKitIcon(combatant, kitName);
			iterator.remove();
			i++;
		}
		
		if(kitNames.size() > 0) {
			Icon prevIcon = new PreviousIcon(combatant, this);
			Icon nextIcon = new NextIcon(combatant, new KitSelection(combatant, prevIcon, kitNames));
			this.addNextIcon(nextIcon);
		}
	}

	private void addIcons(Combatant combatant, Icon previous, Set<String> kitNames) {
		super.setSlot(FunctionMenuSlot.C_THREE, new NewKitIcon(combatant, new KitCreation(combatant)));
		
		int i = 0;
		Iterator<String> iterator = kitNames.iterator();
		while (i < 10 && iterator.hasNext()) {
			String kitName = iterator.next();
			this.addKitIcon(combatant, kitName);
			iterator.remove();
			i++;
		}
		
		if(kitNames.size() > 0) {
			Icon prevIcon = new PreviousIcon(combatant, this);
			Icon nextIcon = new NextIcon(combatant, new KitSelection(combatant, prevIcon, kitNames));
			this.addNextIcon(nextIcon);
			this.addPreviousIcon(previous);
		}
	}

	private void addKitIcon(Combatant combatant, String kitName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new KitIcon(combatant, new KitCreation(combatant), kitName));
		} 
	}


	private void addNextIcon(Icon icon) {
		super.setSlot(FunctionMenuSlot.C_FIVE, icon);
	}
	
	private void addPreviousIcon(Icon icon) {
		super.setSlot(FunctionMenuSlot.C_ONE, icon);
	}
}
