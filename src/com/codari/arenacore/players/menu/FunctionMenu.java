package com.codari.arenacore.players.menu;

import java.util.HashMap;
import java.util.Map;

import com.codari.arenacore.players.menu.Abstracts.Menu;
import com.codari.arenacore.players.menu.Icon.Abstracts.AbstractIcon;

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
}
