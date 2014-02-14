package com.codari.apicore.item.manager.interfaces;

import com.codari.apicore.item.asset.interfaces.ItemAsset;

public interface AssetManager<T extends ItemAsset> {
	
	public T getItemAsset(final String assetID) throws Exception;
	
}
