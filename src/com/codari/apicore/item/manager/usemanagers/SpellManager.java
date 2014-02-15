
package com.codari.apicore.item.manager.usemanagers;

import java.util.HashMap;
import java.util.Map;

import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.Spell;

public class SpellManager implements AssetManager<Spell> {
	private final static String DELIMITER = "@";
	
	private Map<String, Spell> spellAssets;
	
	public SpellManager() {
		this.spellAssets = new HashMap<>();
	}
	@Override
	public Spell getItemAsset(final CodariItem item, String assetID) throws Exception {
		String[] parsedString = assetID.split(DELIMITER, 1);
		if(this.spellAssets.containsKey(parsedString[0])) {
			return this.spellAssets.get(parsedString[0]);
		}
		//Return new Item Asset
		return null;
	}

}
