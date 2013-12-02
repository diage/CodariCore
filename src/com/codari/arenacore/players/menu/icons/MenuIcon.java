package com.codari.arenacore.players.menu.icons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.icons.structure.IconType;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.Menu;
import com.codari.arenacore.players.menu.menus.UtilityMenu;

public class MenuIcon extends Icon {
	private FunctionMenu functionMenu;
	private UtilityMenu utilityMenu;
	
	public MenuIcon(Material material, Player player, String displayName) {
		super(material, player, IconType.MENU, displayName);

		this.utilityMenu = null;
		this.functionMenu = null;
	}
	
	public MenuIcon(Material material, Player player, Menu menu, String displayName) {
		super(material, player, IconType.MENU, displayName);
		if(menu instanceof UtilityMenu) {
			this.utilityMenu = (UtilityMenu) menu;
			this.functionMenu = null;
		} else {
			this.functionMenu = (FunctionMenu) menu;
			this.utilityMenu = null;
		}
		
		this.utilityMenu = null;
		this.functionMenu = null;
	}
	
	public MenuIcon(Material material, Player player, FunctionMenu functionMenu, UtilityMenu utilityMenu, String displayName) {
		super(material, player, IconType.MENU, displayName);
		this.functionMenu = functionMenu;
		this.utilityMenu = utilityMenu;

		this.utilityMenu = null;
		this.functionMenu = null;
	}
	
	public void setFunctionMenu(FunctionMenu functionMenu) {
		this.functionMenu = functionMenu;
	}
	
	public void setUtilityMenu(UtilityMenu utilityMenu) {
		this.utilityMenu = utilityMenu;
	}
	
	public UtilityMenu getUtilityMenu() {
		return this.utilityMenu;
	}
	
	public FunctionMenu getFunctionMenu() {
		return this.functionMenu;
	}
	
	public void click() {
		Bukkit.getPluginManager().callEvent(new IconMenuClickEvent(this));
	}
}
