
package com.codari.apicore.item.manager.usemanagers;

import java.util.HashMap;
import java.util.Map;

import com.codari.api5.Codari;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.Spell;
import com.codari.arenacore.LibraryCore;

public class SpellManager implements AssetManager<Spell> {	
	private Map<String, Spell> spellAssets;
	
	public SpellManager() {
		this.spellAssets = new HashMap<>();
	}
	
	@Override
	public Spell getItemAsset(final CodariItem item, String assetID) throws Exception {
		if(this.spellAssets.containsKey(assetID)) {
			return this.spellAssets.get(assetID);
		}
		//Return new Item Asset
		Spell spell = (Spell) ((LibraryCore) Codari.getLibrary()).createItemAsset(assetID);
		this.spellAssets.put(assetID, spell);
		return spell;
	}
}
