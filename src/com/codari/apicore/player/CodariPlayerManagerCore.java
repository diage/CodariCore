package com.codari.apicore.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
	private final Map<UUID, CodariPlayerCore> codariPlayers;
	private final Map<UUID, CodariPlayerCore> onlineCodariPlayers;
	private boolean pendingListenerRegistration;
	private final Map<UUID, PermissionAttachment> perms;
	
	//-----Constructor-----//
	public CodariPlayerManagerCore() {
		this.codariPlayers = new HashMap<>();
		this.onlineCodariPlayers = new HashMap<>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			UUID uuid = player.getUniqueId();
			CodariPlayerCore codariPlayer = new CodariPlayerCore(uuid);
			this.codariPlayers.put(uuid, codariPlayer);
			this.onlineCodariPlayers.put(uuid, codariPlayer);
		}
		pendingListenerRegistration = true;
		this.perms = new HashMap<>();
		ConfigurationSerialization.registerClass(CodariPlayerCore.class);
	}
	
	//-----Methods-----//
	@Override
	public CodariPlayerCore getCodariPlayer(UUID uuid) {
		CodariPlayerCore codariPlayer = this.codariPlayers.get(uuid);
		if (codariPlayer == null) {
			codariPlayer = new CodariPlayerCore(uuid);
			this.codariPlayers.put(uuid, codariPlayer);
			if (codariPlayer.getHandle().isOnline()) {
				this.onlineCodariPlayers.put(uuid, codariPlayer);
			}
		}
		return codariPlayer;
	}
	
	@Override
	public CodariPlayerCore getCodariPlayer(OfflinePlayer player) {
		return this.getCodariPlayer(player.getUniqueId());
	}
	
	@Override
	public boolean isOnline(UUID uuid) {
		return this.onlineCodariPlayers.containsKey(uuid);
	}
	
	@Override
	public boolean isOnline(OfflinePlayer player) {
		return this.isOnline(player.getUniqueId());
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
			UUID uuid = e.getPlayer().getUniqueId();
			CodariPlayerCore codariPlayer = codariPlayers.get(uuid);
			onlineCodariPlayers.put(uuid, codariPlayer);
			perms.put(uuid, e.getPlayer().addAttachment(CodariCore.instance()));
		}
		
		@EventHandler(priority = EventPriority.MONITOR)
		private void onPlayerQuit(PlayerQuitEvent e) {
			UUID uuid = e.getPlayer().getUniqueId();
			CodariPlayerCore codariPlayer = onlineCodariPlayers.remove(uuid);
			if (codariPlayer == null) {
				codariPlayer = codariPlayers.get(uuid);
			}
			codariPlayer.invalidateOnlineReference();
			perms.remove(uuid);
		}
	}

	@Override
	public void setPerm(UUID uuid, String perm, boolean value) {
		if (this.perms.containsKey(uuid)) {
			this.perms.get(uuid).setPermission(perm, value);
		}
	}
}