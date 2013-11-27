package com.codari.apicore;

import org.bukkit.plugin.java.JavaPlugin;

import com.codari.Debugger;
import com.codari.api5.Codari;
import com.codari.api5.CodariException;
import com.codari.api5.util.PlayerReference;
import com.codari.api5.util.reflect.Reflector;
import com.codari.apicore.metadata.MetadataManagerCore;
import com.codari.apicore.stats.StatFactoryCore;
import com.codari.arenacore.ArenaManagerCore;
import com.codari.arenacore.develop.ArenaDevelopmentCommand;
import com.codari.arenacore.develop.NewArenaCommand;
import com.codari.arenacore.players.teams.commands.ComandLeaveTeam;
import com.codari.arenacore.players.teams.commands.CommandCreateTeam;
import com.codari.arenacore.players.teams.commands.CommandInvitePlayerToTeam;

public final class CodariCore extends JavaPlugin implements Codari {
	//-----Static Methods-----//
	public static CodariCore instance() {
		return (CodariCore) Codari.INSTANCE;
	}
	
	//-----Fields-----//
	private MetadataManagerCore metadataManager;
	private ArenaManagerCore arenaManager;
	private StatFactoryCore statFactory;
	
	//-----Enabler-----//
	@Override
	public void onEnable() {
		this.setInstanceAccess(true);
		this.metadataManager = new MetadataManagerCore();
		this.staticInitialization();
		this.arenaManager = new ArenaManagerCore();
		this.statFactory = new StatFactoryCore();
		
		//-----Commands-----//
		super.getCommand("new2v2arena").setExecutor(new NewArenaCommand());
		super.getCommand("arenakit").setExecutor(new ArenaDevelopmentCommand());
		super.getCommand("createteam").setExecutor(new CommandCreateTeam());
		super.getCommand("invite").setExecutor(new CommandInvitePlayerToTeam());
		super.getCommand("leaveteam").setExecutor(new ComandLeaveTeam());
		
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
	
	//-----Private Methods-----//
	private void setInstanceAccess(boolean accessible) {
		try {
			if (accessible) {
				Reflector.writeStaticField(Codari.class, "INSTANCE", this);
			} else {
				Reflector.writeStaticField(Codari.class, "INSTANCE", null);
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
			throw new CodariException("Failed to set instance access", ex);
		}
	}
	
	private void staticInitialization() {
		PlayerReference._();
	}
}