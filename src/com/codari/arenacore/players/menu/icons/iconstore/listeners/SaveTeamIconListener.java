package com.codari.arenacore.players.menu.icons.iconstore.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.teams.create.SelectTeamNameIcon;

public class SaveTeamIconListener implements Listener {
	public static Map<String, String> requestedTeamNames = new HashMap<>();
	
	@EventHandler()
	public void setTeamName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectTeamNameIcon) {
			requestedTeamNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());
		}
	}
}
