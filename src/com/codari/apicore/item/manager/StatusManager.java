package com.codari.apicore.item.manager;

import java.util.HashMap;
import java.util.Map;

import com.codari.api5.Codari;
import com.codari.apicore.item.asset.StatusAsset;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arenacore.LibraryCore;

public class StatusManager implements AssetManager<StatusAsset> {
	private Map<String, StatusAsset> statusAssets;
	
	public StatusManager() {
		this.statusAssets = new HashMap<>();
	}
	
	@Override
	public StatusAsset getItemAsset(final CodariItem item, String assetID) throws Exception {
		if(this.statusAssets.containsKey(assetID)) {
			return this.statusAssets.get(assetID);
		}
		//Return new Item Asset
		StatusAsset statusAsset = (StatusAsset) ((LibraryCore) Codari.getLibrary()).createItemAsset(assetID);
		this.statusAssets.put(assetID, statusAsset);
		return statusAsset;
	}
}
