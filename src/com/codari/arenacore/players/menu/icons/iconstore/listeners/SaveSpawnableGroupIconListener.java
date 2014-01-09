package com.codari.arenacore.players.menu.icons.iconstore.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.SelectNameIcon;

public class SaveSpawnableGroupIconListener implements Listener {
	public static Map<String, String> requestedSpawnableGroupNames = new HashMap<>();
	
	@EventHandler()
	public void setSpawnableGroupName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectNameIcon) {
			requestedSpawnableGroupNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());			
		}
	}	
}
