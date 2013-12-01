package com.codari.arenacore.players.menu.events;

import com.codari.arenacore.players.menu.icons.structure.Icon;

public class IconRequestEvent extends IconInteractEvent {
	private String playerInput;
	
	public IconRequestEvent(Icon icon, String playerInput) {
		super(icon);
		this.playerInput = playerInput;
	}

	public String getPlayerInput() {
		return this.playerInput;
	}
}
