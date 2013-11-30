package com.codari.arenacore.players.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.Menu;
import com.codari.arenacore.players.menu.menus.UtilityMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;
import com.codari.arenacore.players.menu.slots.MenuSlot;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;

@SuppressWarnings("deprecation")
public class MenuManager {
	private Player player;
	private FunctionMenu functionMenu;
	private UtilityMenu utilityMenu;
	
	public MenuManager(Combatant combatant) {
		this.player = combatant.getPlayer();
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
	
	public void setMenuSlot(MenuSlot menuSlot, Icon icon) {
		PlayerInventory playerInventory = this.player.getInventory();
		if(menuSlot instanceof FunctionMenuSlot) {
			this.functionMenu.setSlot(menuSlot, icon);
			playerInventory.setItem(((FunctionMenuSlot) menuSlot).getSlot(), icon);
		} else {
			this.utilityMenu.setSlot(menuSlot, icon);
			playerInventory.setItem(((UtilityMenuSlot) menuSlot).getSlot(), icon);
		}
		this.player.updateInventory();
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
