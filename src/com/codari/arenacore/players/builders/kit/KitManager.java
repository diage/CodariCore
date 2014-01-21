package com.codari.arenacore.players.builders.kit;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.api5.Codari;
import com.codari.arena.rules.ArenaRoleDeclaration;
import com.codari.arenacore.arena.ArenaBuilderCore;
import com.codari.arenacore.arena.ArenaManagerCore;


public class KitManager {
	private KitBuilder selectedBuilder;
	private Map<String, KitBuilder> kitBuilders;
	private Map<String, Kit> kits;

	public KitManager() {
		this.kitBuilders = new LinkedHashMap<>();
		this.kits = new LinkedHashMap<>();
		for(Entry<String, ArenaBuilderCore> arenaBuilderEntry : ((ArenaManagerCore) Codari.getArenaManager()).getArenaBuilders()) {
			this.kits.put(arenaBuilderEntry.getKey(), new Kit(arenaBuilderEntry.getKey(), arenaBuilderEntry.getValue()));
		}
	}

	public void setSelectedKitBuilder(String name) {
		this.selectedBuilder = this.kitBuilders.get(name);
	}

	public KitBuilder getSelectedKitBuilder() {
		return this.selectedBuilder;
	}

	//---For Menu---//
	public void submitKitBuilder(KitBuilder kitBuilder) {
		String kitBuilderName = kitBuilder.getName();
		kitBuilder.addRoleDeclaration(new ArenaRoleDeclaration());	//FIXME - We will be changing this later.
		this.kitBuilders.put(kitBuilderName, kitBuilder);
		this.selectedBuilder = kitBuilder;		
	}	

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

	public boolean createKit(String name) {
		if(this.selectedBuilder != null) {
			Kit newKit = this.selectedBuilder.buildKit(name);
			this.kits.put(name, newKit);
			return true;
		} 
		Bukkit.broadcastMessage(ChatColor.RED + "Kit wasn't constructed because selected Kit Builder is null!");	//TODO
		return false;
	}

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
