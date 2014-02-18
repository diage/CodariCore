package com.codari.apicore.item;

import com.codari.apicore.util.Codec;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.players.combatants.Combatant;

public class CodariItemCore implements CodariItem {

	//-----Fields-----//
	private final Combatant combatant;
	private final String sessionId;
	private final String itemId;
	
	//-----Constructor-----//
	public CodariItemCore(Combatant combatant, String sessionId, int itemId) {
		this.combatant = combatant;
		this.sessionId = sessionId;
		this.itemId = Codec.BASE62.encode(itemId);
	}
	
	//-----Methods-----//
	@Override
	public Combatant getCombatant() {
		return this.combatant;
	}
	
	@Override
	public String getSessionId() {
		return this.sessionId;
	}
	
	@Override
	public String getItemId() {
		return this.itemId;
	}
}
