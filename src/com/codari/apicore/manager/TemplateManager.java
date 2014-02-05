package com.codari.apicore.manager;

import com.codari.apicore.item.ItemAsset;

public abstract class TemplateManager {
	
	public abstract ItemAsset getItemAsset(final String assetID) throws Exception;
	
	public abstract String getSerializedString();
	
	public abstract String getLore();
	
}
