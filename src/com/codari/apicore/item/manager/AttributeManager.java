package com.codari.apicore.item.manager;

import java.util.HashMap;
import java.util.Map;

import com.codari.api5.Codari;
import com.codari.apicore.item.asset.AttributeAsset;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arenacore.LibraryCore;

public class AttributeManager implements AssetManager<AttributeAsset> {
	private Map<String, AttributeAsset> attributeAssets;
	
	public AttributeManager() {
		this.attributeAssets = new HashMap<>();
	}
	
	@Override
	public AttributeAsset getItemAsset(final CodariItem item, String assetID) throws Exception {
		if(this.attributeAssets.containsKey(assetID)) {
			return this.attributeAssets.get(assetID);
		}
		//Return new Item Asset
		AttributeAsset attributeAsset = (AttributeAsset) ((LibraryCore) Codari.getLibrary()).createItemAsset(assetID);
		this.attributeAssets.put(assetID, attributeAsset);
		return attributeAsset;
	}
}

