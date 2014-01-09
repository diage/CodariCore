package com.codari.arenacore.players.menu.icons.iconstore.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.queue.SelectQueueIcon;

public class SelectQueueIconListener implements Listener {
	public static Map<String, String> requeustedQueueNames = new HashMap<>();
	
	@EventHandler()
	public void selectQueue(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectQueueIcon) {
			requeustedQueueNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());
		}
	}
}
