package com.codari.apicore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.codari.Debugger;
import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.CodariException;
import com.codari.api5.util.PlayerReference;
import com.codari.api5.util.reflect.Reflector;
import com.codari.apicore.metadata.MetadataManagerCore;
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
import com.codari.arenacore.develop.NewArenaCommand;
import com.codari.arenacore.players.teams.commands.ComandLeaveTeam;
import com.codari.arenacore.players.teams.commands.CommandCreateTeam;
import com.codari.arenacore.players.teams.commands.CommandInvitePlayerToTeam;

public final class CodariCore extends JavaPlugin implements CodariI {
	//-----Static Methods-----//
	public static CodariCore instance() {
		return (CodariCore) CodariI.INSTANCE;
	}
	
	//-----Fields-----//
	private MetadataManagerCore metadataManager;
	private ArenaManagerCore arenaManager;
	private StatFactoryCore statFactory;
	private LibraryCore library;
	
	//-----Enabler-----//
	@Override
	public void onEnable() {
		this.setInstanceAccess(true);
		this.metadataManager = new MetadataManagerCore();
		this.staticInitialization();
		this.arenaManager = new ArenaManagerCore();
		this.statFactory = new StatFactoryCore();
		this.library = new LibraryCore(); 
		
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
		
		//-----Listeners-----//
		Bukkit.getPluginManager().registerEvents(new ArenaDevelopmentKitListener(), this);
		
		//-----Commands-----//
		//---Arena Construction Commands---//
		super.getCommand("new2v2arena").setExecutor(new NewArenaCommand());
		super.getCommand("arenakit").setExecutor(new ArenaDevelopmentCommand());
		//---Team Commands---//
		super.getCommand("createteam").setExecutor(new CommandCreateTeam());
		super.getCommand("invite").setExecutor(new CommandInvitePlayerToTeam());
		super.getCommand("leaveteam").setExecutor(new ComandLeaveTeam());
		super.getCommand("joinarena").setExecutor(new JoinQueueCommand());
				
		//-----TODO debugger-----//
		Debugger.debug();
	}
	
	//-----Disabler-----//
	@Override
	public void onDisable() {
		this.setInstanceAccess(false);
		//PlayerReference.kickAllPlayers();
	}
	
	//-----Public Methods-----//
	@Override
	public MetadataManagerCore getMetadataManager() {
		return this.metadataManager;
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
	
	//-----Private Methods-----//
	private void setInstanceAccess(boolean accessible) {
		try {
			if (accessible) {
				Reflector.writeStaticField(CodariI.class, "INSTANCE", this);
			} else {
				Reflector.writeStaticField(CodariI.class, "INSTANCE", null);
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
			throw new CodariException("Failed to set instance access", ex);
		}
	}
	
	private void staticInitialization() {
		PlayerReference._();
	}
}