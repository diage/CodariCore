package com.codari.arenacore.players.menu.menus;

import java.util.HashMap;
import java.util.Map;

import com.codari.arenacore.players.menu.Icon.Abstracts.AbstractIcon;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;
import com.codari.arenacore.players.menu.slots.MenuSlot;

public class FunctionMenu implements Menu {
	private Map<FunctionMenuSlot, AbstractIcon> icons;
	
	public FunctionMenu() {
		this.icons = new HashMap<>();
	}
	
	@Override
	public AbstractIcon getIcon(MenuSlot menuSlot) {
		if(menuSlot instanceof FunctionMenuSlot) {
			return this.icons.get(menuSlot);
		}
		return null;
	}

	@Override
	public void setSlot(MenuSlot menuSlot, AbstractIcon icon) {
		if(menuSlot instanceof FunctionMenuSlot) {
			this.icons.put((FunctionMenuSlot) menuSlot, icon);
		}
	}

	@Override
	public void setMenu(Map<MenuSlot, AbstractIcon> icons) {
		Map<FunctionMenuSlot, AbstractIcon> tempIcons = new HashMap<>();
		for(MenuSlot menuSlot : icons.keySet()) {
			if(!(menuSlot instanceof FunctionMenuSlot)) {
				return;
			}
			tempIcons.put((FunctionMenuSlot) menuSlot, icons.get(menuSlot));
		}
		this.icons = tempIcons;
	}
}
