package com.codari.apicore.item.manager.interfaces;

import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.ItemAsset;

public interface AssetManager<T extends ItemAsset> {
	
	public T getItemAsset(final CodariItem item, final String assetID) throws Exception;
	
}
