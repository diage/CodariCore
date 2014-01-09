package com.codari.arenacore.players.menu;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.menu.menus.menustore.kitbuilders.KitBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.KitCreationBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.KitSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupEditSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupSelection;
import com.codari.arenacore.players.menu.menus.menustore.teams.ArenaSelection;
import com.codari.arenacore.players.menu.menus.menustore.teams.InitialTeamOptions;
import com.codari.arenacore.players.menu.menus.menustore.teams.PlayerSelection;

public class DynamicMenuManager {
	private Combatant combatant;
	
	//-----Dynamic Menus-----//
	private KitSelection kitSelectionMenu;
	private KitBuilderSelection kitBuilderSelectionMenu;
	private KitCreationBuilderSelection kitCreationBuilderSelectionMenu;
	private SpawnableGroupSelection spawnableGroupSelectionMenu;
	private SpawnableGroupEditSelection spawnableGroupEditSelectionMenu;
	private PlayerSelection playerSelectionMenu;
	private InitialTeamOptions initialTeamOptionsMenu;
	private ArenaSelection arenaSelectionMenu;
	
	public DynamicMenuManager(Combatant combatant) {
		this.combatant = combatant;
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
	
	public void setSpawnableGroupSelectionMenu(SpawnableGroupSelection spawnableGroupSelection) {
		this.spawnableGroupSelectionMenu = spawnableGroupSelection;
	}
	
	public void setSpawnableGroupEditSelectionMenu(SpawnableGroupEditSelection spawnableGroupEditSelection) {
		this.spawnableGroupEditSelectionMenu = spawnableGroupEditSelection;
	}
	
	public void addSpawnableGroupIcon(String groupName) {
		this.spawnableGroupSelectionMenu.addSpawnableGroupIcon(this.combatant, groupName);
		this.spawnableGroupEditSelectionMenu.addSpawnableGroupIcon(this.combatant, groupName);
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
	
	public void setArenaSelectionMenu(ArenaSelection arenaSelection) {
		this.arenaSelectionMenu = arenaSelection;
	}
	
	public void addArenaIcons(String arenaName) {
		this.arenaSelectionMenu.addArenaIcon(this.combatant, arenaName);
	}
}
