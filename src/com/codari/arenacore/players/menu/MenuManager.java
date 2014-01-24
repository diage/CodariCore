package com.codari.arenacore.players.menu;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
	private boolean inMenu;
	private FunctionMenu functionMenu;
	private UtilityMenu utilityMenu;
	private MenuIcon mainMenuIcon;
	private Map<FunctionMenuSlot, Icon> currentFunctionInventory;
	private Map<UtilityMenuSlot, Icon> currentUtilityInventory;
	private Map<FunctionMenuSlot, ItemStack> savedInventoryLeft;
	private Map<UtilityMenuSlot, ItemStack> savedInventoryRight;

	public MenuManager(Combatant combatant) {	
		this.combatant = combatant;
		this.functionMenu = new FunctionMenu(combatant);
		this.utilityMenu = new UtilityMenu(combatant);
		this.inMenu = false;
		this.currentFunctionInventory = this.functionMenu.getIcons();
		this.currentUtilityInventory = this.utilityMenu.getIcons();
		this.savedInventoryLeft = new HashMap<>();
		this.savedInventoryRight = new HashMap<>();

		this.setMenuIconExit();
	}

	public void setMenu(Menu menu) {
		if(menu instanceof FunctionMenu) {
			this.functionMenu = (FunctionMenu) menu;
			this.currentFunctionInventory = ((FunctionMenu)menu).getIcons();
			if(this.inMenu) {this.resetFunctionMenu();}
		} else {
			this.utilityMenu = (UtilityMenu) menu;
			this.currentUtilityInventory = ((UtilityMenu)menu).getIcons();
			if(this.inMenu) {this.resetUtilityMenu();}
		}
	}

	public void setMenuSlot(MenuSlot menuSlot, Icon icon) {
		if(this.inMenu) {
			PlayerInventory playerInventory = this.combatant.getPlayer().getInventory();
			if(menuSlot instanceof FunctionMenuSlot) {
				this.functionMenu.setSlot(menuSlot, icon);
				this.currentFunctionInventory.put((FunctionMenuSlot)menuSlot, icon);
				playerInventory.setItem(((FunctionMenuSlot) menuSlot).getSlot(), icon);
			} else {
				this.utilityMenu.setSlot(menuSlot, icon);
				this.currentUtilityInventory.put((UtilityMenuSlot) menuSlot, icon);
				playerInventory.setItem(((UtilityMenuSlot) menuSlot).getSlot(), icon);
			}
			this.combatant.getPlayer().updateInventory();
		}
	}

	public void removeMenuSlot(MenuSlot menuSlot) {
		if(this.inMenu) {
			PlayerInventory playerInventory = this.combatant.getPlayer().getInventory();
			if(menuSlot instanceof FunctionMenuSlot) {
				if(this.functionMenu.hasSlot((FunctionMenuSlot) menuSlot)) {
					this.functionMenu.removeIcon((FunctionMenuSlot)menuSlot);
					this.currentFunctionInventory.remove((FunctionMenuSlot)menuSlot);
					playerInventory.setItem(((FunctionMenuSlot) menuSlot).getSlot(), null);
				}
			}
			this.combatant.getPlayer().updateInventory();
		}
	}

	public void enterMenu() {
		if(!this.inMenu) {
			this.getInventory();
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
			this.getInventory();
			this.inMenu = true;

			this.utilityMenu = utilityMenu;
			this.currentUtilityInventory = utilityMenu.getIcons();
			this.resetUtilityMenu();

			if(this.functionMenu != null) {
				this.resetFunctionMenu();
			}
			this.setMenuIconEnter();
		}
	}

	public void enterMenu(UtilityMenu utilityMenu, FunctionMenu functionMenu) {
		if(!this.inMenu) {
			this.getInventory();
			this.inMenu = true;

			this.utilityMenu = utilityMenu;
			this.currentUtilityInventory = utilityMenu.getIcons();
			this.resetUtilityMenu();

			this.functionMenu = functionMenu;
			this.currentFunctionInventory = functionMenu.getIcons();
			this.resetFunctionMenu();
			this.setMenuIconEnter();
		}
	}

	public void saveExitMenu() {
		if(this.inMenu) {
			this.setInventory();
			this.inMenu = false;
			this.setMenuIconExit();
		}
	}

	public void exitMenu() {
		if(this.inMenu) {
			this.setInventory();
			this.functionMenu = new FunctionMenu(this.combatant);
			this.currentFunctionInventory = this.functionMenu.getIcons();
			this.utilityMenu = new UtilityMenu(this.combatant);
			this.currentUtilityInventory = this.utilityMenu.getIcons();
			this.inMenu = false;
			this.setMenuIconExit();
		}
	}

	private void resetFunctionMenu() {
		PlayerInventory playerInventory = this.combatant.getPlayer().getInventory();
		for(FunctionMenuSlot menuSlot : FunctionMenuSlot.values()){
			if(menuSlot != FunctionMenuSlot.NO_SLOT) {
				playerInventory.setItem(menuSlot.getSlot(), this.functionMenu.getIcon(menuSlot));
			}
		}
		this.combatant.getPlayer().updateInventory();
	}

	private void resetUtilityMenu() {
		PlayerInventory playerInventory = this.combatant.getPlayer().getInventory();
		for(UtilityMenuSlot menuSlot : UtilityMenuSlot.values()){
			if(menuSlot != UtilityMenuSlot.NO_SLOT) {
				playerInventory.setItem(menuSlot.getSlot(), this.utilityMenu.getIcon(menuSlot));
			}
		}
		this.combatant.getPlayer().updateInventory();
	}

	private void setMenuIconEnter() {
		this.mainMenuIcon = new MenuIcon(Material.MAGMA_CREAM, this.combatant, ChatColor.DARK_AQUA + "Main Menu");
		this.combatant.getPlayer().getInventory().setItem(8, this.mainMenuIcon);
		this.combatant.getPlayer().updateInventory();
	}

	private void setMenuIconExit() {
		this.mainMenuIcon = new MenuIcon(Material.MAGMA_CREAM, combatant, this.functionMenu, this.utilityMenu, ChatColor.DARK_AQUA + "Main Menu");
		this.combatant.getPlayer().getInventory().setItem(8, this.mainMenuIcon);
		this.combatant.getPlayer().updateInventory();
	}

	private void getInventory() {
		Player player = this.combatant.getPlayer();
		for(UtilityMenuSlot menuSlot : UtilityMenuSlot.values()) {
			if(menuSlot != UtilityMenuSlot.NO_SLOT) {
				this.savedInventoryRight.put(menuSlot, player.getInventory().getItem(menuSlot.getSlot()));
			}
		}
		for(FunctionMenuSlot menuSlot : FunctionMenuSlot.values()) {
			if(menuSlot != FunctionMenuSlot.NO_SLOT) {
				this.savedInventoryLeft.put(menuSlot, player.getInventory().getItem(menuSlot.getSlot()));
			}
		}
	}

	private void setInventory() {
		Player player = this.combatant.getPlayer();
		for(UtilityMenuSlot menuSlot : UtilityMenuSlot.values()) {
			if(menuSlot != UtilityMenuSlot.NO_SLOT) {
				player.getInventory().setItem(menuSlot.getSlot(), this.savedInventoryRight.get(menuSlot));
			}
		}
		for(FunctionMenuSlot menuSlot : FunctionMenuSlot.values()) {
			if(menuSlot != FunctionMenuSlot.NO_SLOT) {
				player.getInventory().setItem(menuSlot.getSlot(), this.savedInventoryLeft.get(menuSlot));
			}
		}
		player.updateInventory();
	}	

	public Icon click(MenuSlot menuSlot) {
		if(this.inMenu) {
			if(menuSlot instanceof FunctionMenuSlot) {
				return this.currentFunctionInventory.get(menuSlot);
			} else if(menuSlot instanceof UtilityMenuSlot) {
				return this.currentUtilityInventory.get(menuSlot);
			}
		}
		return null;
	}

	public boolean isMenuOpen() {
		return this.inMenu;
	}	
}
