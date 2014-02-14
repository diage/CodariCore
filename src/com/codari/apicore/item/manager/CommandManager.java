package com.codari.apicore.item.manager;

import java.util.HashMap;
import java.util.Map;

import com.codari.apicore.item.asset.CommandAsset;
import com.codari.apicore.item.manager.interfaces.AssetManager;

public class CommandManager implements AssetManager<CommandAsset> {
	private Map<String, CommandAsset> commandAssets;
	
	public CommandManager() {
		this.commandAssets = new HashMap<>();
	}
	
	@Override
	public CommandAsset getItemAsset(final String assetID) throws Exception {
		if(this.commandAssets.containsKey(assetID)) {
			return this.commandAssets.get(assetID);
		}
		//Return new Item Asset
		return null;
	}

}
