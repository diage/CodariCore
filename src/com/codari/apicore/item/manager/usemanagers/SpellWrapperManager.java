package com.codari.apicore.item.manager.usemanagers;

import com.codari.apicore.item.asset.useassets.SpellWrapperAsset;
import com.codari.apicore.item.asset.useassets.UseAsset;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;

public class SpellWrapperManager implements AssetManager<UseAsset> {
	private SpellManager spellManager;
	
	public SpellWrapperManager() {
		this.spellManager = new SpellManager();
	}

	@Override
	public UseAsset getItemAsset(CodariItem item, String assetID) throws Exception {
		return new SpellWrapperAsset(item, this.spellManager.getItemAsset(item, assetID));
	}
}
