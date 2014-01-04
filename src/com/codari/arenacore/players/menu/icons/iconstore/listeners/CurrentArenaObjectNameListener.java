package com.codari.arenacore.players.menu.icons.iconstore.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.ArenaObjectRandomIcon;

public class CurrentArenaObjectNameListener implements Listener {
	public static Map<String, String> currentRandomArenaObjectNames = new HashMap<>();
	
	@EventHandler()
	public void setCurrentRandomArenaObjectName(IconMenuClickEvent e) {
		if(e.getIcon() instanceof ArenaObjectRandomIcon) {
			currentRandomArenaObjectNames.put(e.getIcon().getPlayerName(), ((ArenaObjectRandomIcon)e.getIcon()).getRandomArenaObjectName());
			Bukkit.broadcastMessage(ChatColor.GREEN + "Current random arena object name is " + ((ArenaObjectRandomIcon)e.getIcon()).getRandomArenaObjectName());
		}
	}
}
