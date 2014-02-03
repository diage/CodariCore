package com.codari.apicore.player;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.util.org.apache.commons.lang3.builder.HashCodeBuilder;
import net.minecraft.util.org.apache.commons.lang3.builder.ToStringBuilder;
import net.minecraft.util.org.apache.commons.lang3.builder.ToStringStyle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import com.codari.api5.player.CodariPlayer;
import com.codari.apicore.CodariCore;

@SerializableAs("Codari_Player")
public final class CodariPlayerCore implements CodariPlayer {
	//-----Fields-----//
	private WeakReference<OfflinePlayer> playerReference;
	private final String name;
	private boolean isvalidOnlineReference;
	private final String toString;
	private final int hash;
	
	//-----Constructor-----//
	public CodariPlayerCore(String name) {
		OfflinePlayer player = Bukkit.getOfflinePlayer(name);
		this.playerReference = new WeakReference<>(player);
		this.name = player.getName();
		this.isvalidOnlineReference = true;
		this.toString = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.name).build();
		this.hash = new HashCodeBuilder().append(this.name).build();
	}
	
	//-----Methods-----//
	@Override
	public OfflinePlayer getHandle() {
		OfflinePlayer player = this.playerReference.get();
		if (player == null) {
			player = this.updateReference();
		}
		return player;
	}
	
	private OfflinePlayer updateReference() {
		OfflinePlayer player = Bukkit.getOfflinePlayer(this.name);
		this.playerReference = new WeakReference<>(player);
		this.isvalidOnlineReference = true;
		return player;
	}
	
	void invalidateOnlineReference() {
		this.isvalidOnlineReference = false;
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
		OfflinePlayer player = this.getHandle();
		if (player instanceof Player && this.isvalidOnlineReference) {
			return (Player) player;
		} else if (this.isOnline()) {
			player = this.updateReference();
			try {
				return (Player) player;
			} catch (ClassCastException ignore) {
				throw new Error("Player named " + this.name + " is falsly shown online");
			}
		}
		return null;
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
		return CodariCore.instance().getCodariPlayerManager().isOnline(this);
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
	
	//-----Utility Methods-----//
	@Override
	public String toString() {
		return this.toString;
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
		if (obj instanceof CodariPlayerCore) {
			return this.name.equalsIgnoreCase(((CodariPlayerCore) obj).name);
		}
		return false;
	}
	
	//-----Serialization-----//
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("name", this.name);
		return result;
	}
	
	public CodariPlayerCore deserialize(Map<String, Object> args) {
		return CodariCore.instance().getCodariPlayerManager().getCodariPlayer((String) args.get("name"));
	}
}