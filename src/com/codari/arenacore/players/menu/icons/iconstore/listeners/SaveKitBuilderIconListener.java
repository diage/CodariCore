package com.codari.arenacore.players.menu.icons.iconstore.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.newkitbuildercreation.SelectKitBuilderNameIcon;

public class SaveKitBuilderIconListener implements Listener {
	public static Map<String, String> requestedKitBuilderNames = new HashMap<>();
	
	@EventHandler()
	public void setKitName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectKitBuilderNameIcon) {
			requestedKitBuilderNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());			
		}
	}
}
