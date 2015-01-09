package com.codari.apicore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.util.reflect.Reflector;
import com.codari.apicore.asset.AssetLybraryCore;
import com.codari.apicore.attribute.AttributeFactoryCore;
import com.codari.apicore.command.CodariCommandCenter;
import com.codari.apicore.command.CommandRegister;
import com.codari.apicore.item.CodariItemManagerCore;
import com.codari.apicore.player.CodariPlayerManagerCore;
import com.codari.arena.objects.RoleSwitchListenerObject;
import com.codari.arena.objects.gate.Gate;
import com.codari.arena.objects.itemspawner.MainItemSpawner;
import com.codari.arena.objects.itemspawner.structure.ItemSpawnerListener;
import com.codari.arena.objects.objectives.DiamondObjectivePoint;
import com.codari.arena.objects.objectives.EmeraldObjectivePoint;
import com.codari.arena.objects.objectives.GoldObjectivePoint;
import com.codari.arena.objects.objectives.IronObjectivePoint;
import com.codari.arena.objects.objectives.structure.ObjectivePointListener;
import com.codari.arena.objects.traps.ExplosionTrap;
import com.codari.arena.objects.traps.FireTrap;
import com.codari.arena.objects.traps.PoisonSnareTrap;
import com.codari.arena.objects.traps.structure.TrapListener;
import com.codari.arena.players.RoleHotbarListener;
import com.codari.arena.players.skills.DamageReductionSkill;
import com.codari.arena.players.skills.TeleportSkill;
import com.codari.arena.rules.WinCondition2v2;
import com.codari.arena5.players.guilds.GuildManager;
import com.codari.arena5.players.teams.TeamManager;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.arena.objects.RoleSelectionObject;
import com.codari.arenacore.arena.objects.SpawnPoint;
import com.codari.arenacore.develop.FinalizeCommand;
import com.codari.arenacore.develop.NewArenaCommand;
import com.codari.arenacore.players.builders.kit.KitManager;
import com.codari.arenacore.players.guilds.GuildManagerCore;
import com.codari.arenacore.players.menu.MenuListener;
import com.codari.arenacore.players.menu.events.listeners.IconListenerRegister;
import com.codari.arenacore.players.menu.hotbar.HotbarListener;
import com.codari.arenacore.players.role.RoleManagerCore;
import com.codari.arenacore.players.skills.SkillListener;
import com.codari.arenacore.players.teams.TeamManagerCore;
import com.codari.arenacore.players.teams.commands.ComandLeaveTeam;
import com.codari.arenacore.players.teams.commands.CommandCheckTeam;
import com.codari.arenacore.players.teams.commands.CommandCreateTeam;
import com.codari.arenacore.players.teams.commands.CommandInvitePlayerToTeam;
import com.codari.arenacore.players.teams.queue.JoinQueueCommand;

public final class CodariCore extends JavaPlugin implements CodariI {
	//-----Static Methods-----//
	public static CodariCore instance() {
		return (CodariCore) CodariI.INSTANCE;
	}
	
	//-----Fields-----//
	private AssetLybraryCore assetLybrary;
	private CodariPlayerManagerCore codariPlayerManager;
	private ArenaManagerCore arenaManager;
	private KitManager kitManager;
	private RoleManagerCore roleManager;
	private TeamManagerCore teamManager;
	private GuildManagerCore guildManager;
	private AttributeFactoryCore attributeFactory;
	private LibraryCore library;
	private CodariItemManagerCore itemDataManager;
	
	//-----Loader-----//
	@Override
	public void onLoad() {
		this.codariPlayerManager = new CodariPlayerManagerCore();
	}
	
	private CommandRegister commandRegister;
	
	//-----Enabler-----//
	@Override
	public void onEnable() {
		this.setInstanceAccess(true);
		//this.assetLybrary = new AssetLybraryCore();
		this.codariPlayerManager.registerPlayerListener();
		this.itemDataManager = new CodariItemManagerCore();
		this.library = new LibraryCore(); 
		this.roleManager = new RoleManagerCore();
		this.arenaManager = new ArenaManagerCore();
		this.kitManager = new KitManager();
		this.teamManager = new TeamManagerCore();
		this.guildManager = new GuildManagerCore();
		this.teamManager = new TeamManagerCore();
		this.guildManager = new GuildManagerCore();
		this.attributeFactory = new AttributeFactoryCore();
		this.commandRegister = new CommandRegister();
		//new StatRegistry();
		
		//---Arena Objects---//
		Codari.getLibrary().registerArenaObject(SpawnPoint.class);
		Codari.getLibrary().registerArenaObject(RoleSwitchListenerObject.class);
		Codari.getLibrary().registerArenaObject(Gate.class);
		Codari.getLibrary().registerArenaObject(MainItemSpawner.class);
		Codari.getLibrary().registerArenaObject(DiamondObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(EmeraldObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(GoldObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(IronObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(ExplosionTrap.class);
		Codari.getLibrary().registerArenaObject(FireTrap.class);
		Codari.getLibrary().registerArenaObject(PoisonSnareTrap.class);
		Codari.getLibrary().registerArenaObject(RoleSelectionObject.class);
		Codari.getLibrary().registerWinCondition(WinCondition2v2.class);
		
		//---Skills---//
		Codari.getLibrary().registerSkill(DamageReductionSkill.class);
		Codari.getLibrary().registerSkill(TeleportSkill.class);	
		
		//---Events---//
		Bukkit.getPluginManager().registerEvents(new ObjectivePointListener(), this);
		Bukkit.getPluginManager().registerEvents(new TrapListener(), this);
		Bukkit.getPluginManager().registerEvents(new ItemSpawnerListener(), this);
		Bukkit.getPluginManager().registerEvents(new HotbarListener(), this);
		Bukkit.getPluginManager().registerEvents(new RoleHotbarListener(), this);
		Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
		new IconListenerRegister(this);
		//Bukkit.getPluginManager().registerEvents(new AntiTroyListener(), this);
		
		//-----Listeners-----//
		Bukkit.getPluginManager().registerEvents(new SkillListener(), this);
		Bukkit.getPluginManager().registerEvents(new CoreListener(), this);
		
		//-----Commands-----//
		//---Arena Construction Commands---//
		super.getCommand("ca").setExecutor(new CodariCommandCenter());
		
		this.commandRegister.registerCommand(JoinQueueCommand.COMMAND_NAME, new JoinQueueCommand());
		this.commandRegister.registerCommand(NewArenaCommand.COMMAND_NAME, new NewArenaCommand());
		this.commandRegister.registerCommand(FinalizeCommand.COMMAND_NAME, new FinalizeCommand());
		this.commandRegister.registerCommand(CommandCreateTeam.COMMAND_NAME, new CommandCreateTeam());
		this.commandRegister.registerCommand(CommandInvitePlayerToTeam.COMMAND_NAME, new CommandInvitePlayerToTeam());
		this.commandRegister.registerCommand(ComandLeaveTeam.COMMAND_NAME, new ComandLeaveTeam());
		this.commandRegister.registerCommand(CommandCheckTeam.COMMAND_NAME, new CommandCheckTeam());
		this.commandRegister.registerCommand(FinalizeCommand.LOAD_NAME, new FinalizeCommand());
		
		//-----Serialization-----//
		this.roleManager.load_EJDNAE__E();
		this.arenaManager.load_D_D_D_D();
	}
	
	//-----Disabler-----//
	@Override
	public void onDisable() {
		this.arenaManager.saveArenaBuilders();
		this.arenaManager.saveGameRules();
		this.roleManager.saveRoles();
		this.setInstanceAccess(false);
	}
	
	//-----Public Methods-----//
	public CodariItemManagerCore getItemDataManager() {
		return this.itemDataManager;
	}
	
	@Override
	public CodariPlayerManagerCore getCodariPlayerManager() {
		return this.codariPlayerManager;
	}
	
	@Override
	public ArenaManagerCore getArenaManager() {
		return this.arenaManager;
	}
	
	public KitManager getKitManager() {
		return this.kitManager;
	}
	
	public RoleManagerCore getRoleManager() {
		return this.roleManager;
	}
	
	@Override
	public TeamManager getTeamManager() {
		return this.teamManager;
	}
	
	@Override
	public GuildManager getGuildManager() {
		return this.guildManager;
	}
	
	@Override
	public AttributeFactoryCore getAttributeFactory() {
		return this.attributeFactory;
	}
	
	@Override
	public LibraryCore getLibrary() {
		return this.library;
	}
	
	public CommandRegister getCommandRegister() {
		return this.commandRegister;
	}

	@Override
	public AssetLybraryCore getAssetLybrary() {
		return this.assetLybrary;
	}
	
	//-----Private Methods-----//
	private void setInstanceAccess(boolean accessible) {
		if (accessible) {
			Reflector.writeStaticField(CodariI.class, "INSTANCE", this);
		} else {
			Reflector.writeStaticField(CodariI.class, "INSTANCE", null);
		}
	}
}
