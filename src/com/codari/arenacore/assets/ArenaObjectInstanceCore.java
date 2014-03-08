package com.codari.arenacore.assets;

import org.bukkit.Location;

import com.codari.apicore.CodariCore;
import com.codari.arena5.assets.ArenaObjectAsset;
import com.codari.arena5.assets.instance.ArenaObjectInstance;

public class ArenaObjectInstanceCore implements ArenaObjectInstance {
	//-----Fields-----//
	private final ArenaObjectAsset asset;
	private final Location location;
	
	public ArenaObjectInstanceCore(String registration, String type, String name, Location location) {
		this.asset = (ArenaObjectAsset) CodariCore.instance().getAssetLybrary().getAsset(registration, type, name);
		if (this.asset == null) {
			//TODO throw exception
		}
		this.location = location;
	}
}
