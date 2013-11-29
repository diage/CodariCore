package com.codari.arenacore.players.menu;

import java.util.Map;

import com.codari.arenacore.players.menu.Abstracts.Menu;
import com.codari.arenacore.players.menu.Icon.Abstracts.AbstractIcon;


public class UtilityMenu implements Menu {
	private Map<UtilityMenuSlot, AbstractIcon> icons;
	
	@Override
	public AbstractIcon getIcon(MenuSlot menuSlot) {
		if(menuSlot instanceof UtilityMenuSlot) {
			return this.icons.get(menuSlot);
		}
		return null;
	}

	@Override
	public void setSlot(MenuSlot menuSlot, AbstractIcon icon) {
		if(menuSlot instanceof UtilityMenuSlot) {
			this.icons.put((UtilityMenuSlot) menuSlot, icon);
		}
	}
}
