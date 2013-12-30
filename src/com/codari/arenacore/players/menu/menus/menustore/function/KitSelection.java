package com.codari.arenacore.players.menu.menus.menustore.function;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.KitIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.NewKitIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class KitSelection extends FunctionMenu {
	private List<String> kitNames = new ArrayList<>();
	
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
	private KitSelection(Combatant combatant, List<String> kitNames) {
		super(combatant);
		this.kitNames = kitNames;
		this.addIcons(combatant);
		this.addPreviousIcon(combatant);
	}
	
	private void addIcons(Combatant combatant) {
		super.setSlot(FunctionMenuSlot.C_THREE, new NewKitIcon(combatant, new KitCreation(combatant)));
		
		this.kitNames.addAll(((CombatantCore)combatant).getKitManager().getKits().keySet());
		if(this.kitNames.size() <= 10) {
			for(int i = 0; i < this.kitNames.size(); i++) {
				this.addKitIcon(combatant, this.kitNames.get(0));
				this.kitNames.remove(0);
			}
		} else {
			for(int i = 0; i < 9; i++) {
				this.addKitIcon(combatant, this.kitNames.get(0));
				this.kitNames.remove(0);
			}	
			this.addNextIcon(combatant);
		}
	}
	
	private void addKitIcon(Combatant combatant, String kitName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new KitIcon(combatant, new KitCreation(combatant), kitName));
		} 
	}
	
	private void addPreviousIcon(Combatant combatant) {
		List<String> tempList = new ArrayList<>();
		tempList.addAll(((CombatantCore)combatant).getKitManager().getKits().keySet());
		int index = tempList.indexOf(this.kitNames.get(0)) - 10;
		Bukkit.broadcastMessage(ChatColor.GREEN + "Should be a multiple of 10: " + index);	//TODO
		if(index == 0) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "Second page!");	//TODO
			super.setSlot(FunctionMenuSlot.C_ONE, new PreviousIcon(combatant, new KitSelection(combatant)));
		} else {
			for(int i = 0; i < index; i ++) {
				tempList.remove(index);
			}
			super.setSlot(FunctionMenuSlot.C_ONE, new PreviousIcon(combatant, new KitSelection(combatant, tempList)));			
		}
	}
	
	private void addNextIcon(Combatant combatant) {
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, new KitSelection(combatant, this.kitNames)));
	}
}
