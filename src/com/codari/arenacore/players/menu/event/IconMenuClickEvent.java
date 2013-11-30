package com.codari.arenacore.players.menu.event;

import com.codari.arenacore.players.menu.icon.IconType;
import com.codari.arenacore.players.menu.icon.abstracts.Icon;
import com.codari.arenacore.players.menu.icon.abstracts.MenuIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.UtilityMenu;

public class IconMenuClickEvent extends IconUpdateEvent {

	public IconMenuClickEvent(Icon icon) {
		super(icon);
	}
	
	public FunctionMenu getFunctionMenu() {
		if(this.icon.getIconType().equals(IconType.MENU)) {
			return ((MenuIcon)this.icon).getFunctionMenu();
		}
		return null;
	}
	
	public UtilityMenu getUtilityMenu() {
		if(this.icon.getIconType().equals(IconType.MENU)) {
			return ((MenuIcon)this.icon).getUtilityMenu();
		}
		return null;
	}	
}
