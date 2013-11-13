package com.codari.apicore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.bukkit.plugin.java.JavaPlugin;

import com.codari.Debugger;
import com.codari.api.Codari;
import com.codari.api.CodariException;
import com.codari.api.util.PlayerReference;
import com.codari.apicore.metadata.MetadataManagerCore;
import com.codari.arenacore.ArenaManagerCore;

public final class CodariCore extends JavaPlugin implements Codari {
	//-----Static Methods-----//
	public static CodariCore instance() {
		return (CodariCore) Codari.INSTANCE;
	}
	
	//-----Fields-----//
	private final Field instanceField;
	private MetadataManagerCore metadataManager;
	private ArenaManagerCore arenaManager;
	
	//-----Constructor-----//
	public CodariCore() {
		try {
			this.instanceField = Codari.class.getDeclaredField("INSTANCE");
		} catch (NoSuchFieldException | SecurityException ex) {
			throw new CodariException("Failed to locate instance field", ex);
		}
	}
	
	//-----Enabler-----//
	@Override
	public void onEnable() {
		this.setInstanceAccess(true);
		this.metadataManager = new MetadataManagerCore();
		this.staticInitialization();
		this.arenaManager = new ArenaManagerCore();
		
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
	
	//-----Private Methods-----//
	private void setInstanceAccess(boolean accessible) {
		try {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(this.instanceField, this.instanceField.getModifiers() & ~Modifier.FINAL);
			if (accessible) {
				this.instanceField.set(null, this);
			} else {
				this.instanceField.set(null, null);
			}
		} catch (NoSuchFieldException | SecurityException |
				IllegalArgumentException | IllegalAccessException ex) {
			throw new CodariException("Failed to set instance access", ex);
		}
	}
	
	private void staticInitialization() {
		PlayerReference._();
	}
}