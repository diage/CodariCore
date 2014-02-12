package com.codari.apicore.manager;

import java.util.HashMap;
import java.util.Map;

import com.codari.apicore.item.ItemAsset;

public class CommandManager extends TemplateManager {
	private Map<String, ItemAsset> itemAssets;
	
	public CommandManager() {
		this.itemAssets = new HashMap<>();
	}
	
	@Override
	public ItemAsset getItemAsset(final String assetID) throws Exception {
		if(this.itemAssets.containsKey(assetID)) {
			return this.itemAssets.get(assetID);
		}
		//Return new Item Asset
		return null;
	}
	
	@Override
	public String getSerializedString() {
		return null;
	}

	@Override
	public String getLore() {
		// TODO Auto-generated method stub
		return null;
	}

}
