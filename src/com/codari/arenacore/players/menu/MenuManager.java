package com.codari.arenacore.players.menu;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.Menu;
import com.codari.arenacore.players.menu.menus.UtilityMenu;
import com.codari.arenacore.players.menu.menus.menustore.kitbuilders.KitBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.KitCreationBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.KitSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupEditSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupSelection;
import com.codari.arenacore.players.menu.menus.menustore.teams.InitialTeamOptions;
import com.codari.arenacore.players.menu.menus.menustore.teams.PlayerSelection;
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
	private Map<FunctionMenuSlot, Icon> currentFunctionInventory;
	private Map<UtilityMenuSlot, Icon> currentUtilityInventory;
	
	//-----Dynamic Menus-----//
	private Map<String, KitSelection> kitSelectionMenus;
	private Map<String, KitBuilderSelection> kitBuilderSelectionMenus;
	private Map<String, KitCreationBuilderSelection> kitCreationBuilderSelectionMenus;
	private Map<String, SpawnableGroupSelection> spawnableGroupSelectionMenus;
	private Map<String, SpawnableGroupEditSelection> spawnableGroupEditSelectionMenus;
	private Map<String, PlayerSelection> playerSelectionMenus;
	private Map<String, InitialTeamOptions> initialTeamOptionsMenus;
	
	public MenuManager(Combatant combatant) {
		this.combatant = combatant;
		this.functionMenu = new FunctionMenu(combatant);
		this.utilityMenu = new UtilityMenu(combatant);
		this.inMenu = false;
		this.currentFunctionInventory = this.functionMenu.getIcons();
		this.currentUtilityInventory = this.utilityMenu.getIcons();
		
		this.setMenuIconExit();
		
		this.kitSelectionMenus = new HashMap<>();
		this.kitBuilderSelectionMenus = new HashMap<>();
		this.kitCreationBuilderSelectionMenus = new HashMap<>();
		this.spawnableGroupSelectionMenus = new HashMap<>();
		this.spawnableGroupEditSelectionMenus = new HashMap<>();
		this.playerSelectionMenus = new HashMap<>();
		this.initialTeamOptionsMenus = new HashMap<>();
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
			this.savedInventory = this.combatant.getPlayer().getInventory();
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
			this.combatant.getPlayer().getInventory().setContents(this.savedInventory.getContents());
			this.inMenu = false;
			this.setMenuIconExit();
		}
	}
	
	public void exitMenu() {
		if(this.inMenu) {
			this.combatant.getPlayer().getInventory().setContents(this.savedInventory.getContents());
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
		this.mainMenuIcon = new MenuIcon(Material.BED, this.combatant, ChatColor.GRAY + "Main Menu");
		this.combatant.getPlayer().getInventory().setItem(8, this.mainMenuIcon);
		this.combatant.getPlayer().updateInventory();
	}
	
	private void setMenuIconExit() {
		this.mainMenuIcon = new MenuIcon(Material.BED, combatant, this.functionMenu, this.utilityMenu, "Main Menu");
		this.combatant.getPlayer().getInventory().setItem(8, this.mainMenuIcon);
		this.combatant.getPlayer().updateInventory();
	}
	
	public Icon click(MenuSlot menuSlot) {
		if(menuSlot instanceof FunctionMenuSlot) {
			return this.currentFunctionInventory.get(menuSlot);
		} else if(menuSlot instanceof UtilityMenuSlot) {
			return this.currentUtilityInventory.get(menuSlot);
		}
		return null;
	}
	
	public boolean isMenuOpen() {
		return this.inMenu;
	}	
	
	//-----Dynamic Menus-----//
	public void setKitSelectionMenu(Combatant combatant, KitSelection kitSelection) {
		this.kitSelectionMenus.put(combatant.getPlayerReference().getName(), kitSelection);
	}
	
	public void addKitIcon(Combatant combatant, String kitName) {
		this.kitSelectionMenus.get(combatant.getPlayerReference().getName()).addKitIcon(combatant, kitName);
	}
	
	public void setKitBuilderSelectionMenu(Combatant combatant, KitBuilderSelection kitBuilderSelection) {
		this.kitBuilderSelectionMenus.put(combatant.getPlayerReference().getName(), kitBuilderSelection);
	}
	
	public void setKitCreationBuilderSelectionMenu(Combatant combatant, KitCreationBuilderSelection kitCreationBuilderSelection) {
		this.kitCreationBuilderSelectionMenus.put(combatant.getPlayerReference().getName(), kitCreationBuilderSelection);
	}
	
	public void addKitBuilderIcon(Combatant combatant, String kitBuilderName) {
		this.kitBuilderSelectionMenus.get(combatant.getPlayerReference().getName()).addKitBuilderIcon(combatant, kitBuilderName);
		this.kitCreationBuilderSelectionMenus.get(combatant.getPlayerReference().getName()).addKitCreationBuilderIcon(combatant, kitBuilderName);
	}
	
	public void setSpawnableGroupSelectionMenu(Combatant combatant, SpawnableGroupSelection spawnableGroupSelection) {
		this.spawnableGroupSelectionMenus.put(combatant.getPlayerReference().getName(), spawnableGroupSelection);
	}
	
	public void setSpawnableGroupEditSelectionMenu(Combatant combatant, SpawnableGroupEditSelection spawnableGroupEditSelection) {
		this.spawnableGroupEditSelectionMenus.put(combatant.getPlayerReference().getName(), spawnableGroupEditSelection);
	}
	
	public void addSpawnableGroupIcon(Combatant combatant, String groupName) {
		this.spawnableGroupSelectionMenus.get(combatant.getPlayerReference().getName()).addSpawnableGroupIcon(combatant, groupName);
		this.spawnableGroupEditSelectionMenus.get(combatant.getPlayerReference().getName()).addSpawnableGroupIcon(combatant, groupName);
	}	
	
	public void setPlayerSelectionMenu(Combatant combatant, PlayerSelection playerSelection) {
		this.playerSelectionMenus.put(combatant.getPlayerReference().getName(), playerSelection);
	}
	
	public void addPlayerIcon(Combatant combatant, String playerName) {
		this.playerSelectionMenus.get(combatant.getPlayerReference().getName()).addPlayerIcon(combatant, playerName);
	}
	
	public void setInitialTeamOptionsMenu(Combatant combatant, InitialTeamOptions initialTeamOptions) {
		this.initialTeamOptionsMenus.put(combatant.getPlayerReference().getName(), initialTeamOptions);
	}
	
	public void addInvitationIcons(Combatant combatant, Team team) {
		this.initialTeamOptionsMenus.get(combatant.getPlayerReference().getName()).addInvitationIcons(combatant, team);
	}
}
