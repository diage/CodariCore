package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.options.arguments;

import org.bukkit.Material;

import com.codari.arena5.arena.rules.Argument;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class WinConditionArgumentIcon extends RequestIcon {
	private int argumentIndex;
	private Argument argumentType;
	private String description;
	
	public WinConditionArgumentIcon(Material material, Combatant combatant, String displayName, int argumentIndex, Argument argumentType, String description) {
		super(material, combatant, displayName);
		this.argumentIndex = argumentIndex;
		this.argumentType = argumentType;
		this.description = description;
	}

	@Override
	public String getConversationString() {
		return this.description;
	}
	
	public int getArgumentIndex() {
		return this.argumentIndex;
	}

	public Argument getArgumentType() {
		return this.argumentType;
	}
}
