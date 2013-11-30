package com.codari.arenacore.players.menu.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.codari.arenacore.players.menu.icon.abstracts.Icon;

public class IconUpdateEvent extends Event {
	//-----Static Fields-----//
		private static final HandlerList handlers = new HandlerList();
		
		//-----Static Methods-----//
		public static HandlerList getHandlerList() {
			return IconUpdateEvent.handlers;
		}
		
		protected Icon icon;
		
		public IconUpdateEvent(Icon icon) {
			this.icon = icon;
		}

		public Icon getIcon() {
			return this.icon;
		}
		
		@Override
		public HandlerList getHandlers() {
			return IconUpdateEvent.handlers;
		}	
}
