package com.codari.arenacore.players.menu.events;

import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.icons.structure.IconType;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.UtilityMenu;

public class IconMenuClickEvent extends IconInteractEvent {	
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
