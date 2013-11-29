package com.codari.arenacore.players.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.codari.arenacore.players.menu.Abstracts.Menu;
import com.codari.arenacore.players.menu.Icon.Abstracts.AbstractIcon;

@SuppressWarnings("deprecation")
public class MenuManager {
	private Player player;
	private FunctionMenu functionMenu;
	private UtilityMenu utilityMenu;
	
	public MenuManager(Player player) {
		this.player = player;
		this.functionMenu = new FunctionMenu();
		this.utilityMenu = new UtilityMenu();
	}
	
	public void setMenu(Menu menu) {
		if(menu instanceof FunctionMenu) {
			this.functionMenu = (FunctionMenu) menu;
			this.resetFunctionMenu();
		} else {
			this.utilityMenu = (UtilityMenu) menu;
			this.resetUtilityMenu();
		}
	}
	
	public void setMenuSlot(MenuSlot menuSlot, AbstractIcon icon) {
		PlayerInventory playerInventory = this.player.getInventory();
		if(menuSlot instanceof FunctionMenuSlot) {
			this.functionMenu.setSlot(menuSlot, icon);
			playerInventory.setItem(((FunctionMenuSlot) menuSlot).getSlot(), icon);
		} else {
			this.utilityMenu.setSlot(menuSlot, icon);
			playerInventory.setItem(((UtilityMenuSlot) menuSlot).getSlot(), icon);
		}
	}
	
	private void resetFunctionMenu() {
		PlayerInventory playerInventory = this.player.getInventory();
		for(FunctionMenuSlot menuSlot : FunctionMenuSlot.values()){
			playerInventory.setItem(menuSlot.getSlot(), this.functionMenu.getIcon(menuSlot));
		}
		this.player.updateInventory();
	}
	
	private void resetUtilityMenu() {
		PlayerInventory playerInventory = this.player.getInventory();
		for(UtilityMenuSlot menuSlot : UtilityMenuSlot.values()){
			playerInventory.setItem(menuSlot.getSlot(), this.utilityMenu.getIcon(menuSlot));
		}
		this.player.updateInventory();
	}
}