package com.codari.arenacore.players.menu.icon.abstracts;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arenacore.players.menu.icon.IconType;
import com.codari.arenacore.players.menu.event.IconMenuClickEvent;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.Menu;
import com.codari.arenacore.players.menu.menus.UtilityMenu;

public abstract class MenuIcon extends Icon {
	private FunctionMenu functionMenu;
	private UtilityMenu utilityMenu;
	
	public MenuIcon(Material material, Player player) {
		super(material, player, IconType.MENU);
		this.utilityMenu = null;
		this.functionMenu = null;
	}
	
	public MenuIcon(Material material, Player player, Menu menu) {
		super(material, player, IconType.MENU);
		if(menu instanceof UtilityMenu) {
			this.utilityMenu = (UtilityMenu) menu;
			this.functionMenu = null;
		} else {
			this.functionMenu = (FunctionMenu) menu;
			this.utilityMenu = null;
		}
	}
	
	public MenuIcon(Material material, Player player, FunctionMenu functionMenu, UtilityMenu utilityMenu) {
		super(material, player, IconType.MENU);
		this.functionMenu = functionMenu;
		this.utilityMenu = utilityMenu;
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
