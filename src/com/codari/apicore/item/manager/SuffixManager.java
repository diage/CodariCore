package com.codari.apicore.item.manager;

import java.util.HashMap;
import java.util.Map;

import com.codari.api5.Codari;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.Suffix;
import com.codari.arenacore.LibraryCore;

public class SuffixManager implements AssetManager<Suffix> {
	private Map<String, Suffix> suffixAssets;
	
	public SuffixManager() {
		this.suffixAssets = new HashMap<>();
	}
	
	@Override
	public Suffix getItemAsset(final CodariItem item, String assetID) throws Exception {
		if(this.suffixAssets.containsKey(assetID)) {
			return this.suffixAssets.get(assetID);
		}
		//Return new Item Asset
		Suffix statusAsset = (Suffix) ((LibraryCore) Codari.getLibrary()).createItemAsset(assetID);
		this.suffixAssets.put(assetID, statusAsset);
		return statusAsset;
	}
}
