package com.codari.arenacore.players.menu.menus;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.BorderIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.ExitIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.KitBuildersIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.KitsIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.menustore.function.kitbuilders.KitBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.function.kits.KitSelection;
import com.codari.arenacore.players.menu.slots.MenuSlot;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;


public class UtilityMenu implements Menu {
	protected Map<UtilityMenuSlot, Icon> icons;
	
	public UtilityMenu(Combatant combatant) {
		this.icons = new HashMap<>();
		this.icons.put(UtilityMenuSlot.SEP_ONE, new BorderIcon(combatant));
		this.icons.put(UtilityMenuSlot.SEP_TWO, new BorderIcon(combatant));
		this.icons.put(UtilityMenuSlot.SEP_THREE, new BorderIcon(combatant));
		this.addIcons(combatant);
	}
	
	private void addIcons(Combatant combatant) {
		this.setSlot(UtilityMenuSlot.ONE, new KitsIcon(combatant, new KitSelection(combatant)));
		this.setSlot(UtilityMenuSlot.TWO, new KitBuildersIcon(combatant, new KitBuilderSelection(combatant)));		
		this.setSlot(UtilityMenuSlot.NINE, new ExitIcon(Material.OBSIDIAN, combatant));		
	}
	
	@Override
	public Icon getIcon(MenuSlot menuSlot) {
		if(menuSlot instanceof UtilityMenuSlot) {
			return this.icons.get(menuSlot);
		}
		return null;
	}

	@Override
	public void setSlot(MenuSlot menuSlot, Icon icon) {
		if(menuSlot instanceof UtilityMenuSlot) {
			this.icons.put((UtilityMenuSlot) menuSlot, icon);
		}
	}

	@Override
	public void setMenu(Map<MenuSlot, Icon> icons) {
		Map<UtilityMenuSlot, Icon> tempIcons = new HashMap<>();
		for(MenuSlot menuSlot : icons.keySet()) {
			if(!(menuSlot instanceof UtilityMenuSlot)) {
				return;
			}
			tempIcons.put((UtilityMenuSlot) menuSlot, icons.get(menuSlot));
		}
		this.icons = tempIcons;
	}

	@Override
	public boolean isFull() {
		return this.icons.size() == 12;
	}

	@Override
	public int size() {
		return this.icons.size();
	}
	
	public Map<UtilityMenuSlot, Icon> getIcons() {
		return this.icons;
	}	
}
