package com.codari.arenacore.players.menu.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.codari.arenacore.players.menu.icons.structure.Icon;

public class IconInteractEvent extends Event {
	//-----Static Fields-----//
	private static final HandlerList handlers = new HandlerList();

	//-----Static Methods-----//
	public static HandlerList getHandlerList() {
		return IconInteractEvent.handlers;
	}

	protected Icon icon;

	public IconInteractEvent(Icon icon) {
		this.icon = icon;
	}

	public Icon getIcon() {
		return this.icon;
	}

	@Override
	public HandlerList getHandlers() {
		return IconInteractEvent.handlers;
	}	
}
