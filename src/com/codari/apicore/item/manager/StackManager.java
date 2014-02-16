package com.codari.apicore.item.manager;

import com.codari.apicore.item.asset.StackAsset;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;

public class StackManager implements AssetManager<StackAsset> {	
	
	@Override
	public StackAsset getItemAsset(final CodariItem item, String assetID) throws Exception {
		//Return new Item Asset
		return new StackAsset(assetID);
	}
	
}
