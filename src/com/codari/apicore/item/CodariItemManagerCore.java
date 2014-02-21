package com.codari.apicore.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import com.codari.apicore.item.assetmaster.AssetManagerMaster;
import com.codari.apicore.item.assetmaster.DefinedAsset;
import com.codari.apicore.util.Codec;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.CodariItemManager;
import com.codari.arena5.players.combatants.Combatant;

public class CodariItemManagerCore implements CodariItemManager {
	private final static String DELIMITER = "#";
	
	//-----Fields-----//
	private final Map<Integer, CodariItemCore> data;
	private String sessionId;
	private int itemId;
	private AssetManagerMaster assetMaster;
	
	//-----Constructor-----//
	public CodariItemManagerCore() {
		this.data = new HashMap<>();
		this.newSession();
		this.assetMaster = new AssetManagerMaster();
	}
	
	//-----Methods-----//
	private void newSession() {
		this.sessionId = Codec.BASE62.encode(System.currentTimeMillis());
		this.itemId = 0;
		this.data.clear();
	}
	
	public CodariItem getItem(Combatant combatant, ItemStack item) {
		String displayName = item.getItemMeta().getDisplayName();
		String[] arguments = displayName.split(DELIMITER, 1);
		
		if(arguments[0].equals(this.sessionId)) {
			return this.data.get(Integer.parseInt(arguments[1]));
		} else {
			List<String> lore = item.getItemMeta().getLore();
			List<DefinedAsset> itemAssets = null;
			
			displayName = this.sessionId + DELIMITER + this.itemId;
			CodariItemCore codariItem = new CodariItemCore(combatant, item);
			
			try {
				itemAssets = this.assetMaster.getItemAsset(codariItem, lore);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			if(itemAssets != null) {
				try {
					codariItem.setAssets(itemAssets);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			this.data.put(this.itemId, codariItem);
			this.itemId++;
			return codariItem;
		}
	}
}
