package com.codari.arenacore.players.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.Menu;
import com.codari.arenacore.players.menu.menus.UtilityMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;
import com.codari.arenacore.players.menu.slots.MenuSlot;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;

@SuppressWarnings("deprecation")
public class MenuManager {
	private Combatant combatant;
	private PlayerInventory savedInventory;
	private boolean inMenu;
	private FunctionMenu functionMenu;
	private UtilityMenu utilityMenu;
	private MenuIcon mainMenuIcon;
	
	public MenuManager(Combatant combatant) {
		this.combatant = combatant;
		this.functionMenu = new FunctionMenu();
		this.utilityMenu = new UtilityMenu(combatant);
		this.inMenu = false;
		
		this.setMenuIconExit();
	}
	
	public void setMenu(Menu menu) {
		if(menu instanceof FunctionMenu) {
			this.functionMenu = (FunctionMenu) menu;
			if(this.inMenu) {this.resetFunctionMenu();}
		} else {
			this.utilityMenu = (UtilityMenu) menu;
			if(this.inMenu) {this.resetUtilityMenu();}
		}
	}
	
	public void setMenuSlot(MenuSlot menuSlot, Icon icon) {
		if(this.inMenu) {
			PlayerInventory playerInventory = this.combatant.getPlayer().getInventory();
			if(menuSlot instanceof FunctionMenuSlot) {
				this.functionMenu.setSlot(menuSlot, icon);
				playerInventory.setItem(((FunctionMenuSlot) menuSlot).getSlot(), icon);
			} else {
				this.utilityMenu.setSlot(menuSlot, icon);
				playerInventory.setItem(((UtilityMenuSlot) menuSlot).getSlot(), icon);
			}
			this.combatant.getPlayer().updateInventory();
		}
	}
	
	public void enterSavedMenu() {
		if(!this.inMenu) {
			this.savedInventory = this.combatant.getPlayer().getInventory();
			
			if(this.utilityMenu != null) {
				this.resetUtilityMenu();
				if(this.functionMenu != null) {
					this.resetFunctionMenu();
				}
				this.inMenu = true;
				this.setMenuIconEnter();
			}
		}
	}
	
	public void enterMenu(UtilityMenu utilityMenu) {
		if(!this.inMenu) {
			this.savedInventory = this.combatant.getPlayer().getInventory();
			this.inMenu = true;
			
			this.utilityMenu = utilityMenu;
			this.resetUtilityMenu();
			
			if(this.functionMenu != null) {
				this.resetFunctionMenu();
			}
			this.setMenuIconEnter();
		}
	}
	
	public void enterMenu(UtilityMenu utilityMenu, FunctionMenu functionMenu) {
		if(!this.inMenu) {
			this.savedInventory = this.combatant.getPlayer().getInventory();
			this.inMenu = true;
			
			this.utilityMenu = utilityMenu;
			this.resetUtilityMenu();
			
			this.functionMenu = functionMenu;
			this.resetFunctionMenu();
			this.setMenuIconEnter();
		}
	}
	
	public void saveExitMenu() {
		if(this.inMenu) {
			this.combatant.getPlayer().getInventory().setContents(this.savedInventory.getContents());
			this.inMenu = false;
			this.setMenuIconExit();
		}
	}
	
	public void exitMenu() {
		if(this.inMenu) {
			this.combatant.getPlayer().getInventory().setContents(this.savedInventory.getContents());
			this.functionMenu = new FunctionMenu();
			this.utilityMenu = new UtilityMenu(this.combatant);
			this.inMenu = false;
			this.setMenuIconExit();
		}
	}
	
	private void resetFunctionMenu() {
		PlayerInventory playerInventory = this.combatant.getPlayer().getInventory();
		for(FunctionMenuSlot menuSlot : FunctionMenuSlot.values()){
			playerInventory.setItem(menuSlot.getSlot(), this.functionMenu.getIcon(menuSlot));
		}
		this.combatant.getPlayer().updateInventory();
	}
	
	private void resetUtilityMenu() {
		PlayerInventory playerInventory = this.combatant.getPlayer().getInventory();
		for(UtilityMenuSlot menuSlot : UtilityMenuSlot.values()){
			playerInventory.setItem(menuSlot.getSlot(), this.utilityMenu.getIcon(menuSlot));
		}
		this.combatant.getPlayer().updateInventory();
	}
	
	private void setMenuIconEnter() {
		this.mainMenuIcon = new MenuIcon(Material.BED, this.combatant, ChatColor.GRAY + "Main Menu");
		this.combatant.getPlayer().getInventory().setItem(8, this.mainMenuIcon);
		this.combatant.getPlayer().updateInventory();
	}
	
	private void setMenuIconExit() {
		this.mainMenuIcon = new MenuIcon(Material.BED, combatant, this.functionMenu, this.utilityMenu, "Main Menu");
		this.combatant.getPlayer().getInventory().setItem(8, this.mainMenuIcon);
		this.combatant.getPlayer().updateInventory();
	}
}
