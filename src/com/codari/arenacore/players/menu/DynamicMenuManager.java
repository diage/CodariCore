package com.codari.arenacore.players.menu;

import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.guilds.Guild;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.menus.menustore.guilds.InitialGuildOptions;
import com.codari.arenacore.players.menu.menus.menustore.kitbuilders.KitBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.kitbuilders.TimedActionSettings;
import com.codari.arenacore.players.menu.menus.menustore.kitbuilders.WinConditionSettings;
import com.codari.arenacore.players.menu.menus.menustore.kits.KitCreationBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.KitSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.RoleAddition;
import com.codari.arenacore.players.menu.menus.menustore.kits.RoleSelectionObjectSettings;
import com.codari.arenacore.players.menu.menus.menustore.kits.RoleSettings;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupEditSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.SpawnableGroupSelection;
import com.codari.arenacore.players.menu.menus.menustore.roles.RoleSelection;
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
	private RoleSelection roleSelectionMenu;
	private PlayerSelection playerSelectionMenu;
	private InitialTeamOptions initialTeamOptionsMenu;
	private InitialGuildOptions initialGuildOptionsMenu;
	private ArenaSelection arenaSelectionMenu;
	private WinConditionSettings winConditionSettingsMenu;
	private TimedActionSettings timedActionSettingsMenu;
	private RoleAddition roleAdditionMenu;
	
	private Map<Kit, SpawnableGroupSelection> spawnableGroupSelectionMenus;
	private Map<Kit, SpawnableGroupEditSelection> spawnableGroupEditSelectionMenus;
	private Map<Kit, RoleSettings> roleSettingsMenus;
	private Map<Kit, RoleSelectionObjectSettings> roleSelectionObjectSettingsMenus;
	
	public DynamicMenuManager(Combatant combatant) {
		this.combatant = combatant;
		this.spawnableGroupSelectionMenus = new HashMap<>();
		this.spawnableGroupEditSelectionMenus = new HashMap<>();
		this.roleSettingsMenus = new HashMap<>();
		this.roleSelectionObjectSettingsMenus = new HashMap<>();
		this.roleAdditionMenu = new RoleAddition(this.combatant);
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
	
	//-----Role-----//
	public void setRoleSelectionMenu(RoleSelection roleSelection) {
		this.roleSelectionMenu = roleSelection;
	}
	
	public void setRoleAdditionMenu(BackIcon backIcon) {
		this.roleAdditionMenu.setBackIcon(backIcon);
	}
	
	public RoleAddition getRoleAdditionMenu() {
		return this.roleAdditionMenu;
	}
	
	public void addRoleIcon(String roleName) {	
		this.roleSelectionMenu.addRoleIcon(this.combatant, roleName);
		this.roleAdditionMenu.addRoleIcon(this.combatant, roleName);
	}
	
	public void setRoleSettingsMenu(Kit kit, RoleSettings roleSettings) {
		this.roleSettingsMenus.put(kit, roleSettings);
	}
	
	public void setRoleSelectionObjectSettingsMenu(Kit kit, RoleSelectionObjectSettings roleSelectionObjectSettings) {
		this.roleSelectionObjectSettingsMenus.put(kit, roleSelectionObjectSettings);
	}
	
	public void addArenaRoleIcon(Kit kit, String roleName) {
		this.roleSettingsMenus.get(kit).addArenaRoleIcon(this.combatant, roleName);
		this.roleSelectionObjectSettingsMenus.get(kit).addArenaRoleIcon(this.combatant, roleName);
	}
	
	public void removeArenaRoleIcon(Kit kit, String roleName) {
		//FIXME
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
	
	public void addTeamInvitationIcons(Team team) {
		this.initialTeamOptionsMenu.addTeamInvitationIcons(team);
	}	
	
	public void removeTeamInvitationIcons() {
		this.initialTeamOptionsMenu.removeTeamInvitationIcons();
	}
	
	public void addHasTeamIcons() {
		this.initialTeamOptionsMenu.setHasTeamIcons();
	}
	
	public void addNoTeamIcons() {
		this.initialTeamOptionsMenu.setNoTeamIcon();
	}
	
	public void setInitialGuildOptionsMenu(InitialGuildOptions initialGuildOptions) {
		this.initialGuildOptionsMenu = initialGuildOptions;
	}
	
	public void addGuildInvitationIcons(Guild guild) {
		this.initialGuildOptionsMenu.addGuildInvitationIcons(guild);
	}
	
	public void removeGuildInvitationIcons() {
		this.initialGuildOptionsMenu.removeGuildInvitationIcons();
	}
	
	public void addHasGuildIcons() {
		this.initialGuildOptionsMenu.setHasGuildIcons();
	}
	
	public void addNoGuildIcons() {
		this.initialGuildOptionsMenu.setNoGuildIcon();
	}
	
	public void setArenaSelectionMenu(ArenaSelection arenaSelection) {
		this.arenaSelectionMenu = arenaSelection;
	}
	
	public void addArenaIcon(String arenaName) {
		if(this.arenaSelectionMenu != null) {
			this.arenaSelectionMenu.addArenaIcon(this.combatant, arenaName);
		}
	}
	
	public void resetKitBuilderDynamicMenus() {
		this.removeAllWinConditionIcons();
		this.removeAllTimedActionIcons();
	}
	
	public void setWinConditionSettingsMenu(WinConditionSettings winConditionSettings) {
		this.winConditionSettingsMenu = winConditionSettings;
	}
	
	public void addWinConditionIcon(String winconditionName) {
		this.winConditionSettingsMenu.addWinConditionIcon(this.combatant, winconditionName);
	}
	
	private void removeAllWinConditionIcons() {
		this.winConditionSettingsMenu.resetMenu(this.combatant);
	}
	
	public void setTimedActionSettingsMenu(TimedActionSettings timedActionSettings) {
		this.timedActionSettingsMenu = timedActionSettings;
	}
	
	public void addTimedActionIcon(String timedAction) {
		this.timedActionSettingsMenu.addTimedActionIcon(this.combatant, timedAction);
	}	
	
	private void removeAllTimedActionIcons() {
		this.timedActionSettingsMenu.resetMenu(this.combatant);
	}
}
