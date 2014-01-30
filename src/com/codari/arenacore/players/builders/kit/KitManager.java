package com.codari.arenacore.players.builders.kit;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.api5.Codari;
import com.codari.arenacore.arena.ArenaBuilderCore;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.arena.rules.GameRuleCore;


public class KitManager {
	private KitBuilder selectedBuilder;
	private Map<String, KitBuilder> kitBuilders;
	private Map<String, Kit> kits;

	public KitManager() {
		this.kitBuilders = new LinkedHashMap<>();
		this.kits = new LinkedHashMap<>();
	}

	public void setSelectedKitBuilder(String name) {
		this.selectedBuilder = this.kitBuilders.get(name);
	}

	public KitBuilder getSelectedKitBuilder() {
		return this.selectedBuilder;
	}

	//---For Menu---//
	public boolean containsKitBuilder(String kitBuilderName) {
		return this.kitBuilders.containsKey(kitBuilderName);
	}

	public Kit getKit(String name) {
		if(!this.kits.containsKey(name)) {
			Bukkit.broadcastMessage(ChatColor.RED + "You got an invalid kit name!!"); //TODO
		}
		return this.kits.get(name);
	}

	public KitBuilder getKitBuilder(String name) {
		if(!this.kitBuilders.containsKey(name)) {
			Bukkit.broadcastMessage(ChatColor.RED + "You got an invalid kit builder name!!"); //TODO
		}
		return this.kitBuilders.get(name);
	}

	//---Menu Kit/KitBuilder Creation---//
	public boolean createKit(String kitName) {
		if(this.selectedBuilder != null) {
			Kit newKit = this.selectedBuilder.buildKit(kitName);
			((ArenaManagerCore) Codari.getArenaManager()).addArenaBuilder(newKit.getArenaBuilder());
			this.kits.put(kitName, newKit);
			return true;
		} 
		Bukkit.broadcastMessage(ChatColor.RED + "Kit wasn't constructed because selected Kit Builder is null!");	//TODO
		return false;
	}
	
	public void submitKitBuilder(KitBuilder kitBuilder) {
		String kitBuilderName = kitBuilder.getName();
		((ArenaManagerCore) Codari.getArenaManager()).registerGameRule(kitBuilder.getGameRule());
		this.kitBuilders.put(kitBuilderName, kitBuilder);
		this.selectedBuilder = kitBuilder;		
	}	
	
	//-----For deserialization : Kit/KitBuilder Creation-----//
	public void createKit(ArenaBuilderCore arenaBuilder) {
		Kit newKit = new Kit(arenaBuilder.getName(), arenaBuilder);
		((ArenaManagerCore) Codari.getArenaManager()).addArenaBuilder(arenaBuilder);
		this.kits.put(arenaBuilder.getName(), newKit);
	}
	
	public void createKitBuilder(GameRuleCore gameRule) {
		KitBuilder kitBuilder = new KitBuilder(gameRule);
		((ArenaManagerCore) Codari.getArenaManager()).registerGameRule(gameRule);
		this.kitBuilders.put(gameRule.getName(), kitBuilder);
	}
	//-----End of deserialization-----//

	public boolean containsKit(String kitName) {
		return this.kits.containsKey(kitName);
	}

	public void renameKit(String oldKitName, String newKitName) {
		if(this.containsKit(oldKitName)) {
			Kit kit = this.kits.get(oldKitName);
			this.kits.remove(oldKitName);
			kit.setName(newKitName);
			this.kits.put(newKitName, kit);
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "You can't rename a kit that doesn't exist in the kit manager!"); //TODO
		}
	}

	public Map<String, Kit> getKits() {
		return new LinkedHashMap<>(this.kits);
	}

	public Map<String, KitBuilder> getKitBuilders() {
		return new LinkedHashMap<>(this.kitBuilders);
	}
}
