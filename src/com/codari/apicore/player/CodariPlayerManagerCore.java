package com.codari.apicore.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.codari.api5.player.CodariPlayerManager;
import com.codari.apicore.CodariCore;

public final class CodariPlayerManagerCore implements CodariPlayerManager {
	//-----Fields-----//
	private final Map<String, OfflineCodariPlayerCore> offlineCodariPlayers;
	private final Map<String, CodariPlayerCore> onlineCodariPlayers;
	
	//-----Constructor-----//
	public CodariPlayerManagerCore() {
		this.offlineCodariPlayers = new HashMap<>();
		this.onlineCodariPlayers = new HashMap<>();
		ConfigurationSerialization.registerClass(OfflineCodariPlayerCore.class);
		ConfigurationSerialization.registerClass(CodariPlayerCore.class);
	}
	
	//-----Public Methods-----//
	@Override
	public OfflineCodariPlayerCore getOfflineCodariPlayer(String name) {
		name = name.toLowerCase();
		OfflineCodariPlayerCore instance = this.offlineCodariPlayers.get(name);
		if (instance == null) {
			instance = new OfflineCodariPlayerCore(name);
			this.offlineCodariPlayers.put(name, instance);
		}
		return instance;
	}
	
	@Override
	public OfflineCodariPlayerCore getOfflineCodariPlayer(OfflinePlayer player) {
		return this.getOfflineCodariPlayer(player.getName());
	}
	
	@Override
	public OfflineCodariPlayerCore[] getOfflineCodariPlayers() {
		OfflineCodariPlayerCore[] offlinePlayers = new OfflineCodariPlayerCore[this.offlineCodariPlayers.size()];
		int i = 0;
		for (Entry<String, OfflineCodariPlayerCore> e : this.offlineCodariPlayers.entrySet()) {
			offlinePlayers[i++] = e.getValue();
		}
		return offlinePlayers;
	}
	
	@Override
	public CodariPlayerCore getCodariPlayer(String name) {
		return this.onlineCodariPlayers.get(name.toLowerCase());
	}
	
	@Override
	public CodariPlayerCore getCodariPlayer(OfflinePlayer player) {
		return this.getCodariPlayer(player.getName());
	}
	
	@Override
	public CodariPlayerCore[] getOnlineCodariPlayers() {
		CodariPlayerCore[] onlinePlayers = new CodariPlayerCore[this.onlineCodariPlayers.size()];
		int i = 0;
		for (Entry<String, CodariPlayerCore> e : this.onlineCodariPlayers.entrySet()) {
			onlinePlayers[i++] = e.getValue();
		}
		return onlinePlayers;
	}
	
	@Override
	public boolean isOnline(String name) {
		return this.onlineCodariPlayers.containsKey(name.toLowerCase());
	}
	
	@Override
	public boolean isOnline(OfflinePlayer player) {
		return this.isOnline(player.getName());
	}
	
	public void registerPlayerListeners() {
		Bukkit.getPluginManager().registerEvents(new PlayerConnectionListeners(), CodariCore.instance());
	}
	
	//-----Player Connection Listeners-----//
	private final class PlayerConnectionListeners implements Listener {
		//-----Event Handlers-----//
		@EventHandler(priority = EventPriority.LOWEST)
		private void playerJoin(PlayerJoinEvent e) {
			OfflineCodariPlayerCore offlineInstance = getOfflineCodariPlayer(e.getPlayer());
			onlineCodariPlayers.put(offlineInstance.getName().toLowerCase(), null);
			offlineInstance.update();
			CodariPlayerCore codariPlayer = new CodariPlayerCore(offlineInstance);
			onlineCodariPlayers.put(offlineInstance.getName().toLowerCase(), codariPlayer);
		}
		
		@EventHandler(priority = EventPriority.MONITOR)
		private void playerQuit(PlayerQuitEvent e) {
			onlineCodariPlayers.remove(e.getPlayer().getName().toLowerCase()).invalidate();
			getOfflineCodariPlayer(e.getPlayer()).update();
		}
	}
}