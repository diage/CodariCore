package com.codari.arenacore.players.menu;

import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.menus.menustore.kitbuilders.KitBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.kitbuilders.TimedActionSettings;
import com.codari.arenacore.players.menu.menus.menustore.kitbuilders.WinConditionSettings;
import com.codari.arenacore.players.menu.menus.menustore.kits.KitCreationBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.KitSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupEditSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupSelection;
import com.codari.arenacore.players.menu.menus.menustore.teams.ArenaSelection;
import com.codari.arenacore.players.menu.menus.menustore.teams.InitialTeamOptions;
import com.codari.arenacore.players.menu.menus.menustore.teams.PlayerSelection;

/** 
 * This class is responsible for managing all the dynamic menus for a combatant.
 * This will allow icons to be added to the player's menu while they are interacting with it.
 * */
public class DynamicMenuManager {
	private Combatant combatant;
	
	//-----Dynamic Menus-----//
	private KitSelection kitSelectionMenu;
	private KitBuilderSelection kitBuilderSelectionMenu;
	private KitCreationBuilderSelection kitCreationBuilderSelectionMenu;
	private PlayerSelection playerSelectionMenu;
	private InitialTeamOptions initialTeamOptionsMenu;
	private ArenaSelection arenaSelectionMenu;
	private WinConditionSettings winConditionSettingsMenu;
	private TimedActionSettings timedActionSettingsMenu;
	
	private Map<Kit, SpawnableGroupSelection> spawnableGroupSelectionMenus;
	private Map<Kit, SpawnableGroupEditSelection> spawnableGroupEditSelectionMenus;
	
	public DynamicMenuManager(Combatant combatant) {
		this.combatant = combatant;
		this.spawnableGroupSelectionMenus = new HashMap<>();
		this.spawnableGroupEditSelectionMenus = new HashMap<>();
	} 
	
	//-----Dynamic Menus-----//
	public void setKitSelectionMenu(KitSelection kitSelection) {
		this.kitSelectionMenu = kitSelection;
	}
	
	public void addKitIcon(Combatant combatant, String kitName) {
		this.kitSelectionMenu.addKitIcon(combatant, kitName);
	}
	
	public void setKitBuilderSelectionMenu(KitBuilderSelection kitBuilderSelection) {
		this.kitBuilderSelectionMenu = kitBuilderSelection;
	}
	
	public void setKitCreationBuilderSelectionMenu(KitCreationBuilderSelection kitCreationBuilderSelection) {
		this.kitCreationBuilderSelectionMenu = kitCreationBuilderSelection;
	}
	
	public void addKitBuilderIcon(String kitBuilderName) {
		this.kitBuilderSelectionMenu.addKitBuilderIcon(this.combatant, kitBuilderName);
		this.kitCreationBuilderSelectionMenu.addKitCreationBuilderIcon(this.combatant, kitBuilderName);
	}
	
	public void setSpawnableGroupSelectionMenu(Kit kit, SpawnableGroupSelection spawnableGroupSelection) {
		this.spawnableGroupSelectionMenus.put(kit, spawnableGroupSelection);
	}
	
	public void setSpawnableGroupEditSelectionMenu(Kit kit, SpawnableGroupEditSelection spawnableGroupEditSelection) {
		this.spawnableGroupEditSelectionMenus.put(kit, spawnableGroupEditSelection);
	}
	
	public void addSpawnableGroupIcon(Kit kit, String groupName) {
		this.spawnableGroupSelectionMenus.get(kit).addSpawnableGroupIcon(this.combatant, groupName);
		this.spawnableGroupEditSelectionMenus.get(kit).addSpawnableGroupIcon(this.combatant, groupName);
	}	
	
	public void setPlayerSelectionMenu(PlayerSelection playerSelection) {
		this.playerSelectionMenu = playerSelection;
	}
	
	public void addPlayerIcon(String playerName) {
		this.playerSelectionMenu.addPlayerIcon(this.combatant, playerName);
	}
	
	public void setInitialTeamOptionsMenu(InitialTeamOptions initialTeamOptions) {
		this.initialTeamOptionsMenu = initialTeamOptions;
	}
	
	public void addInvitationIcons(Team team) {
		this.initialTeamOptionsMenu.addInvitationIcons(this.combatant, team);
	}	
	
	public void addHasTeamIcons() {
		this.initialTeamOptionsMenu.setHasTeamIcons(this.combatant);
	}
	
	public void setArenaSelectionMenu(ArenaSelection arenaSelection) {
		this.arenaSelectionMenu = arenaSelection;
	}
	
	public void addArenaIcon(String arenaName) {
		if(this.arenaSelectionMenu != null) {
			this.arenaSelectionMenu.addArenaIcon(this.combatant, arenaName);
		}
	}
	
	public void setWinConditionSettingsMenu(WinConditionSettings winConditionSettings) {
		this.winConditionSettingsMenu = winConditionSettings;
	}
	
	public void addWinConditionIcon(String winconditionName) {
		this.winConditionSettingsMenu.addWinConditionIcon(this.combatant, winconditionName);
	}
	
	public void setTimedActionSettingsMenu(TimedActionSettings timedActionSettings) {
		this.timedActionSettingsMenu = timedActionSettings;
	}
	
	public void addTimedActionIcon(String timedAction) {
		this.timedActionSettingsMenu.addTimedActionIcon(this.combatant, timedAction);
	}	
}
