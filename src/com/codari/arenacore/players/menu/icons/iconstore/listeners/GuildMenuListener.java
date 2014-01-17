package com.codari.arenacore.players.menu.icons.iconstore.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.create.SelectGuildNameIcon;
import com.codari.arenacore.players.menu.icons.iconstore.guilds.edit.SelectPlayerIcon;

public class GuildMenuListener implements Listener {
	public static Map<String, String> requestedGuildNames = new HashMap<>();
	public static Map<String, String> requestedSelectPlayerNames = new HashMap<>();


	@EventHandler()
	private void setGuildName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectGuildNameIcon) {
			requestedGuildNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());
		}
	}

	@EventHandler()
	private void selectPlayer(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectPlayerIcon) {
			requestedSelectPlayerNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());
		}
	}		

}
