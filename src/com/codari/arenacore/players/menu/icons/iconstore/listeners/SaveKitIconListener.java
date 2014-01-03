package com.codari.arenacore.players.menu.icons.iconstore.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.SelectKitNameIcon;

public class SaveKitIconListener implements Listener {
	public static Map<String, String> requestedKitNames = new HashMap<>();
	
	@EventHandler()
	public void setKitName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectKitNameIcon) {
			requestedKitNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());			
		}
	}
}
