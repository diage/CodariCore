package com.codari.arenacore.players.menu.menus;

import java.util.HashMap;
import java.util.Map;

import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.slots.MenuSlot;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;


public class UtilityMenu implements Menu {
	private Map<UtilityMenuSlot, Icon> icons;
	
	public UtilityMenu() {
		this.icons = new HashMap<>();
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
}
