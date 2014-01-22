package com.codari.apicore.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import com.codari.api5.player.CodariPlayerManager;
import com.codari.apicore.CodariCore;

public final class CodariPlayerManagerCore implements CodariPlayerManager {
	//-----Fields-----//
	private final Map<String, CodariPlayerCore> codariPlayers;
	private final Map<String, CodariPlayerCore> onlineCodariPlayers;
	private boolean pendingListenerRegistration;
	private final Map<String, PermissionAttachment> perms;
	
	//-----Constructor-----//
	public CodariPlayerManagerCore() {
		this.codariPlayers = new HashMap<>();
		this.onlineCodariPlayers = new HashMap<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			String name = player.getName().toLowerCase();
			CodariPlayerCore codariPlayer = new CodariPlayerCore(name);
			this.codariPlayers.put(name, codariPlayer);
			this.onlineCodariPlayers.put(name, codariPlayer);
		}
		pendingListenerRegistration = true;
		this.perms = new HashMap<>();
		ConfigurationSerialization.registerClass(CodariPlayerCore.class);
	}
	
	//-----Methods-----//
	@Override
	public CodariPlayerCore getCodariPlayer(String name) {
		name = name.toLowerCase();
		CodariPlayerCore codariPlayer = this.codariPlayers.get(name);
		if (codariPlayer == null) {
			codariPlayer = new CodariPlayerCore(name);
			this.codariPlayers.put(name, codariPlayer);
			if (codariPlayer.getHandle().isOnline()) {
				this.onlineCodariPlayers.put(name, codariPlayer);
			}
		}
		return codariPlayer;
	}
	
	@Override
	public CodariPlayerCore getCodariPlayer(OfflinePlayer player) {
		return this.getCodariPlayer(player.getName());
	}
	
	@Override
	public boolean isOnline(String name) {
		return this.onlineCodariPlayers.containsKey(name.toLowerCase());
	}
	
	@Override
	public boolean isOnline(OfflinePlayer player) {
		return this.isOnline(player.getName());
	}
	
	public void registerPlayerListener() {
		if (this.pendingListenerRegistration) {
			Bukkit.getPluginManager().registerEvents(new PlayerListener(), CodariCore.instance());
			this.pendingListenerRegistration = false;
		}
	}
	
	//-----Player Listener-----//
	private final class PlayerListener implements Listener {
		//-----Event Handlers-----//
		@EventHandler(priority = EventPriority.LOWEST)
		private void onPlayerJoin(PlayerJoinEvent e) {
			String name = e.getPlayer().getName().toLowerCase();
			CodariPlayerCore codariPlayer = codariPlayers.get(name);
			onlineCodariPlayers.put(name, codariPlayer);
			perms.put(name, e.getPlayer().addAttachment(CodariCore.instance()));
		}
		
		@EventHandler(priority = EventPriority.MONITOR)
		private void onPlayerQuit(PlayerQuitEvent e) {
			String name = e.getPlayer().getName().toLowerCase();
			CodariPlayerCore codariPlayer = onlineCodariPlayers.remove(name);
			if (codariPlayer == null) {
				codariPlayer = codariPlayers.get(name);
			}
			codariPlayer.invalidateOnlineReference();
			perms.remove(name);
		}
	}

	@Override
	public void setPerm(String player, String perm, boolean value) {
		player = player.toLowerCase();
		if (this.perms.containsKey(player)) {
			this.perms.get(player).setPermission(perm, value);
		}
	}
}