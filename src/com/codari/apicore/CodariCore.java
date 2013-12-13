package com.codari.apicore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.util.reflect.Reflector;
import com.codari.apicore.player.CodariPlayerManagerCore;
import com.codari.apicore.command.CodariCommandCenter;
import com.codari.apicore.command.CommandRegister;
import com.codari.apicore.stats.StatFactoryCore;
import com.codari.arena.objects.gates.Gate;
import com.codari.arena.objects.itemspawner.MainItemSpawner;
import com.codari.arena.objects.itemspawner.structure.ItemSpawnerListener;
import com.codari.arena.objects.objectives.DiamondObjectivePoint;
import com.codari.arena.objects.objectives.EmeraldObjectivePoint;
import com.codari.arena.objects.objectives.GoldObjectivePoint;
import com.codari.arena.objects.objectives.IronObjectivePoint;
import com.codari.arena.objects.objectives.structure.ObjectivePointListener;
import com.codari.arena.objects.role.switchrole.RoleSwitchListener;
import com.codari.arena.objects.traps.ExplosionTrap;
import com.codari.arena.objects.traps.FireTrap;
import com.codari.arena.objects.traps.PoisonSnareTrap;
import com.codari.arena.objects.traps.structure.TrapListener;
import com.codari.arena.rules.ArenaRoleDeclaration;
import com.codari.arena.rules.WinCondition2v2;
import com.codari.arenacore.ArenaManagerCore;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.commands.JoinQueueCommand;
import com.codari.arenacore.develop.ArenaDevelopmentCommand;
import com.codari.arenacore.develop.ArenaDevelopmentKitListener;
import com.codari.arenacore.develop.FinalizeCommand;
import com.codari.arenacore.develop.NewArenaCommand;
import com.codari.arenacore.players.hotbar.HotbarListener;
import com.codari.arenacore.players.skills.SkillListener;
import com.codari.arenacore.players.teams.commands.ComandLeaveTeam;
import com.codari.arenacore.players.teams.commands.CommandCreateTeam;
import com.codari.arenacore.players.teams.commands.CommandInvitePlayerToTeam;

public final class CodariCore extends JavaPlugin implements CodariI {
	//-----Static Methods-----//
	public static CodariCore instance() {
		return (CodariCore) CodariI.INSTANCE;
	}
	
	//-----Fields-----//
	private CodariPlayerManagerCore codariPlayerManager;
	private ArenaManagerCore arenaManager;
	private StatFactoryCore statFactory;
	private LibraryCore library;
	
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
		this.codariPlayerManager.registerPlayerListeners();
		this.arenaManager = new ArenaManagerCore();
		this.statFactory = new StatFactoryCore();
		this.library = new LibraryCore(); 
		this.commandRegister = new CommandRegister();
		
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
		
		//Bukkit.getPluginManager().registerEvents(new HotbarTest(), this);
		
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
	}
	
	//-----Disabler-----//
	@Override
	public void onDisable() {
		this.setInstanceAccess(false);
		//PlayerReference.kickAllPlayers();
	}
	
	//-----Public Methods-----//
	@Override
	public CodariPlayerManagerCore getCodariPlayerManager() {
		return this.codariPlayerManager;
	}
	
	@Override
	public ArenaManagerCore getArenaManager() {
		return this.arenaManager;
	}
	
	@Override
	public StatFactoryCore getStatFactory() {
		return this.statFactory;
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
