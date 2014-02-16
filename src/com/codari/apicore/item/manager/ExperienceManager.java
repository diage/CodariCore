package com.codari.apicore.item.manager;

import java.util.HashMap;
import java.util.Map;

import com.codari.api5.Codari;
import com.codari.apicore.item.asset.ExperienceAsset;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arenacore.LibraryCore;

public class ExperienceManager implements AssetManager<ExperienceAsset> {
	private Map<String, ExperienceAsset> experienceAssets;
	
	public ExperienceManager() {
		this.experienceAssets = new HashMap<>();
	}
	
	@Override
	public ExperienceAsset getItemAsset(final CodariItem item, String assetID) throws Exception {
		if(this.experienceAssets.containsKey(assetID)) {
			return this.experienceAssets.get(assetID);
		}
		//Return new Item Asset
		ExperienceAsset experienceAsset = (ExperienceAsset) ((LibraryCore) Codari.getLibrary()).createItemAsset(assetID);
		this.experienceAssets.put(assetID, experienceAsset);
		return experienceAsset;
	}
}
