package com.codari.arenacore.players.builders;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.codari.arena5.players.combatants.Combatant;

public class BuildingStartEvent extends Event {
	//-----Static Fields-----//
	private static final HandlerList handlers = new HandlerList();

	//-----Static Methods-----//
	public static HandlerList getHandlerList() {
		return handlers;
	}	
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	//-----Fields-----//
	private final String arenaBuilderName;
	private final Combatant combatant;
	
	//-----Constructor-----//
	public BuildingStartEvent(Combatant combatant, String arenaBuilderName) {
		this.combatant = combatant;
		this.arenaBuilderName = arenaBuilderName;
	}

	//-----Public Methods-----//
	public Combatant getCombatant() {
		return this.combatant;
	}
	
	public String getArenaBuilderName() {
		return this.arenaBuilderName;
	}

}
