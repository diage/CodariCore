package com.codari.apicore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.util.reflect.Reflector;
import com.codari.apicore.attribute.AttributeFactoryCore;
import com.codari.apicore.command.CodariCommandCenter;
import com.codari.apicore.command.CommandRegister;
import com.codari.apicore.enchantment.EnchantmentManagerCore;
import com.codari.apicore.itemdata.ItemDataManagerCore;
import com.codari.apicore.player.CodariPlayerManagerCore;
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
import com.codari.arena.players.roleswitch.RoleSwitchListener;
import com.codari.arena.rules.ArenaRoleDeclaration;
import com.codari.arena.rules.WinCondition2v2;
import com.codari.arena5.players.guilds.GuildManager;
import com.codari.arena5.players.teams.TeamManager;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.arena.statistics.StatRegistry;
import com.codari.arenacore.develop.ArenaDevelopmentCommand;
import com.codari.arenacore.develop.ArenaDevelopmentKitListener;
import com.codari.arenacore.develop.FinalizeCommand;
import com.codari.arenacore.develop.NewArenaCommand;
import com.codari.arenacore.players.guilds.GuildManagerCore;
import com.codari.arenacore.players.menu.MenuListener;
import com.codari.arenacore.players.menu.events.listeners.IconListenerRegister;
import com.codari.arenacore.players.menu.hotbar.HotbarListener;
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
	private CodariPlayerManagerCore codariPlayerManager;
	private ArenaManagerCore arenaManager;
	private TeamManagerCore teamManager;
	private GuildManagerCore guildManager;
	private AttributeFactoryCore attributeFactory;
	private LibraryCore library;
	private EnchantmentManagerCore enchantmentManager;
	private ItemDataManagerCore itemDataManager;
	
	//-----Loader-----//
	@Override
	public void onLoad() {
		this.codariPlayerManager = new CodariPlayerManagerCore();
		this.enchantmentManager = new EnchantmentManagerCore();
	}
	
	private CommandRegister commandRegister;
	
	//-----Enabler-----//
	@Override
	public void onEnable() {
		this.setInstanceAccess(true);
		this.codariPlayerManager.registerPlayerListener();
		this.enchantmentManager.packetStuff();
		this.itemDataManager = new ItemDataManagerCore();
		this.arenaManager = new ArenaManagerCore();
		this.teamManager = new TeamManagerCore();
		this.guildManager = new GuildManagerCore();
		this.attributeFactory = new AttributeFactoryCore();
		this.library = new LibraryCore(); 
		this.commandRegister = new CommandRegister();
		new StatRegistry();
		
		//---Arena Objects---//
		Codari.getLibrary().registerArenaObject(Gate.class);
		Codari.getLibrary().registerArenaObject(MainItemSpawner.class);
		Codari.getLibrary().registerArenaObject(DiamondObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(EmeraldObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(GoldObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(IronObjectivePoint.class);
		Codari.getLibrary().registerArenaObject(ExplosionTrap.class);
		Codari.getLibrary().registerArenaObject(FireTrap.class);
		Codari.getLibrary().registerArenaObject(PoisonSnareTrap.class);
		Codari.getLibrary().registerRoleDeclaration(ArenaRoleDeclaration.class);
		Codari.getLibrary().registerWinCondition(WinCondition2v2.class);
		//---Events---//
		Bukkit.getPluginManager().registerEvents(new RoleSwitchListener(), this);
		Bukkit.getPluginManager().registerEvents(new ObjectivePointListener(), this);
		Bukkit.getPluginManager().registerEvents(new TrapListener(), this);
		Bukkit.getPluginManager().registerEvents(new ItemSpawnerListener(), this);
		Bukkit.getPluginManager().registerEvents(new HotbarListener(), this);
		Bukkit.getPluginManager().registerEvents(new RoleHotbarListener(), this);
		Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
		new IconListenerRegister(this);
		//Bukkit.getPluginManager().registerEvents(new AntiTroyListener(), this);
		
		//-----Listeners-----//
		Bukkit.getPluginManager().registerEvents(new ArenaDevelopmentKitListener(), this);
		Bukkit.getPluginManager().registerEvents(new SkillListener(), this);
		Bukkit.getPluginManager().registerEvents(new CoreListener(), this);
		
		//-----Commands-----//
		//---Arena Construction Commands---//
		super.getCommand("ca").setExecutor(new CodariCommandCenter());
		
		this.commandRegister.registerCommand(JoinQueueCommand.COMMAND_NAME, new JoinQueueCommand());
		this.commandRegister.registerCommand(NewArenaCommand.COMMAND_NAME, new NewArenaCommand());
		this.commandRegister.registerCommand(FinalizeCommand.COMMAND_NAME, new FinalizeCommand());
		this.commandRegister.registerCommand(ArenaDevelopmentCommand.COMMAND_NAME, new ArenaDevelopmentCommand());
		this.commandRegister.registerCommand(CommandCreateTeam.COMMAND_NAME, new CommandCreateTeam());
		this.commandRegister.registerCommand(CommandInvitePlayerToTeam.COMMAND_NAME, new CommandInvitePlayerToTeam());
		this.commandRegister.registerCommand(ComandLeaveTeam.COMMAND_NAME, new ComandLeaveTeam());
		this.commandRegister.registerCommand(CommandCheckTeam.COMMAND_NAME, new CommandCheckTeam());
		this.commandRegister.registerCommand(FinalizeCommand.LOAD_NAME, new FinalizeCommand());
	}
	
	//-----Disabler-----//
	@Override
	public void onDisable() {
		this.setInstanceAccess(false);
		this.enchantmentManager.unregisterCustomEnchantments();
	}
	
	//-----Public Methods-----//
	@Override
	public EnchantmentManagerCore getEnchantmentManager() {
		return this.enchantmentManager;
	}
	
	@Override
	public ItemDataManagerCore getItemDataManager() {
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
	
	//-----Private Methods-----//
	private void setInstanceAccess(boolean accessible) {
		if (accessible) {
			Reflector.writeStaticField(CodariI.class, "INSTANCE", this);
		} else {
			Reflector.writeStaticField(CodariI.class, "INSTANCE", null);
		}
	}
}
