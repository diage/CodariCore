package com.codari.apicore.item.manager;

import java.util.HashMap;
import java.util.Map;

import com.codari.api5.Codari;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.Prefix;
import com.codari.arenacore.LibraryCore;

public class PrefixManager implements AssetManager<Prefix> {
	private Map<String, Prefix> prefixAssets;
	
	public PrefixManager() {
		this.prefixAssets = new HashMap<>();
	}
	
	@Override
	public Prefix getItemAsset(final CodariItem item, String assetID) throws Exception {
		if(this.prefixAssets.containsKey(assetID)) {
			return this.prefixAssets.get(assetID);
		}
		//Return new Item Asset
		Prefix statusAsset = (Prefix) ((LibraryCore) Codari.getLibrary()).createItemAsset(assetID);
		this.prefixAssets.put(assetID, statusAsset);
		return statusAsset;
	}
}
