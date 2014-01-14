package com.codari.arenacore.players.builders;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.codari.arena5.players.combatants.Combatant;

public class BuildingEndEvent extends Event {
	//-----Static Fields-----//
	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	//-----Fields-----//
	private final Combatant combatant;
	
	//-----Constructor-----//
	public BuildingEndEvent(Combatant combatant) {
		this.combatant = combatant;
	}

	//-----Public Methods-----//
	public Combatant getCombatant() {
		return this.combatant;
	}
	
}
