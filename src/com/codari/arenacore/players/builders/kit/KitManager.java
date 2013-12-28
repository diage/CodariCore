package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.players.combatants.Combatant;

public class KitManager {
	private Combatant combatant;
	private KitBuilder selectedBuilder;
	private Map<String, KitBuilder> kitBuilders;
	private Map<String, Kit> kits;
	
	public KitManager(Combatant combatant) {
		this.combatant = combatant;
		this.kitBuilders = new HashMap<>();
		this.kits = new HashMap<>();
	}
	
	public void setSelectedKitBuilder(String name) {
		this.selectedBuilder = this.kitBuilders.get(name);
		KitBuilderListener.changeKitBuilder(combatant, this.selectedBuilder);
	}
	
	public void createKitBuilder(String name) {
		this.selectedBuilder = new KitBuilder(name);
		this.kitBuilders.put(name, this.selectedBuilder);
		KitBuilderListener.changeKitBuilder(combatant, this.selectedBuilder);
	}
	
	public Kit selectKit(String name) {
		return this.kits.get(name);
	}
	
	public Kit createKit(String name) {
		Kit newKit = this.selectedBuilder.buildKit(name);
		this.kits.put(name, newKit);
		return newKit;
	}
}
