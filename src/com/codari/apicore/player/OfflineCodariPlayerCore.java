package com.codari.apicore.player;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import com.codari.api5.io.ConfigurationInput;
import com.codari.api5.io.ConfigurationOutput;
import com.codari.api5.player.CodariPlayers;
import com.codari.api5.player.OfflineCodariPlayer;
import com.codari.api5.util.MetadataUtils;

@SerializableAs("CodariOfflinePlayer")
public final class OfflineCodariPlayerCore implements OfflineCodariPlayer {
	//-----Fields-----//
	private WeakReference<OfflinePlayer> handle;
	private WeakReference<CodariPlayerCore> onlineInstance;
	private final String name;
	private boolean online;
	private final int hash;
	
	//-----Constructor-----//
	public OfflineCodariPlayerCore(String name) {
		this.handle = new WeakReference<>(Bukkit.getOfflinePlayer(name));
		this.onlineInstance = new WeakReference<>(null);
		this.name = this.handle.get().getName();
		this.hash = new HashCodeBuilder()
			.append(this.name.toLowerCase())
			.toHashCode();
	}
	
	//-----Public Methods-----//
	@Override
	public OfflinePlayer getHandle() {
		OfflinePlayer instance = this.handle.get();
		if (instance == null || ((instance instanceof Player) != this.isOnline())) {
			instance = Bukkit.getOfflinePlayer(this.getName());
			this.handle = new WeakReference<>(instance);
		}
		return instance;
	}

	@Override
	public Location getBedSpawnLocation() {
		return this.getHandle().getBedSpawnLocation();
	}

	@Override
	public long getFirstPlayed() {
		return this.getHandle().getFirstPlayed();
	}

	@Override
	public long getLastPlayed() {
		return this.getHandle().getLastPlayed();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Player getPlayer() {
		try {
			return (Player) this.getHandle();
		} catch (ClassCastException ignore) {
			return null;
		}
	}
	
	@Override
	public CodariPlayerCore getCodariPlayer() {
		if (!this.isOnline()) {
			return null;
		}
		CodariPlayerCore onlineInstance = this.onlineInstance.get();
		if (onlineInstance == null || !onlineInstance.isValidPlayer()) {
			onlineInstance = (CodariPlayerCore) CodariPlayers.getCodariPlayer(this);
			this.onlineInstance = new WeakReference<>(onlineInstance);
		}
		return onlineInstance;
	}

	@Override
	public boolean hasPlayedBefore() {
		return this.getHandle().hasPlayedBefore();
	}

	@Override
	public boolean isBanned() {
		return this.getHandle().isBanned();
	}

	@Override
	public boolean isOnline() {
		return this.online;
	}

	@Override
	public boolean isWhitelisted() {
		return this.getHandle().isWhitelisted();
	}

	@Override
	public void setBanned(boolean banned) {
		this.getHandle().setBanned(banned);
	}

	@Override
	public void setWhitelisted(boolean value) {
		this.getHandle().setWhitelisted(value);
	}

	@Override
	public boolean isOp() {
		return this.getHandle().isOp();
	}

	@Override
	public void setOp(boolean value) {
		this.getHandle().setOp(value);
	}

	@Override
	public List<MetadataValue> getMetadata(String metadataKey) {
		return MetadataUtils.getPlayerMetadataStore().getMetadata(this, metadataKey);
	}

	@Override
	public boolean hasMetadata(String metadataKey) {
		return MetadataUtils.getPlayerMetadataStore().hasMetadata(this, metadataKey);
	}

	@Override
	public void removeMetadata(String metadataKey, Plugin owningPlugin) {
		MetadataUtils.getPlayerMetadataStore().removeMetadata(this, metadataKey, owningPlugin);
	}

	@Override
	public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
		MetadataUtils.getPlayerMetadataStore().setMetadata(this, metadataKey, newMetadataValue);
	}
	
	public void update() {
		this.online = CodariPlayers.isOnline(this);
	}
	
	//-----Utility Methods-----//
	@Override
	public String toString() {
		return "CodariOfflinePlayer{name=" + this.getName() + "}";
	}
	
	@Override
	public int hashCode() {
		return this.hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof OfflineCodariPlayerCore) {
			return this.getName().equalsIgnoreCase(((OfflineCodariPlayerCore) obj).getName());
		}
		return false;
	}

	//-----Serialization-----//
	@Override
	public Map<String, Object> serialize() {
		return new ConfigurationOutput()
		.addString("name", this.getName())
		.result();
	}
	
	public static OfflineCodariPlayerCore deserialize(Map<String, Object> args) {
		ConfigurationInput in = new ConfigurationInput(args);
		return (OfflineCodariPlayerCore) CodariPlayers.getOfflineCodariPlayer(in.getString("name"));
	}
}