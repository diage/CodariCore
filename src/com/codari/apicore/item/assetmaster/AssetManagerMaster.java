package com.codari.apicore.item.assetmaster;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.codari.apicore.item.manager.AttributeManager;
import com.codari.apicore.item.manager.ExperienceManager;
import com.codari.apicore.item.manager.StackManager;
import com.codari.apicore.item.manager.StatusManager;
import com.codari.apicore.item.manager.usemanagers.UseManager;
import com.codari.arena5.item.CodariItem;

public class AssetManagerMaster {
	//---Managers---//
	private UseManager useManager;
	private AttributeManager attributeManager;
	private ExperienceManager experienceManager;
	private StackManager stackManager;
	private StatusManager statusManager;
	
	//---Delimiters---//
	private final static String INITIAL_DELIMITER = "$";
	
	public AssetManagerMaster() {
		this.useManager = new UseManager();
		this.attributeManager = new AttributeManager();
		this.experienceManager = new ExperienceManager();
		this.stackManager = new StackManager();
		this.statusManager = new StatusManager();
	}
	
	public List<DefinedAsset> getItemAsset(CodariItem item, List<String> assetIDs) throws Exception {
		List<DefinedAsset> itemAssets = new ArrayList<>();
		int attributeCounter = 0;
		int incrementalCounter = 0;
		int prefixCounter = 0;
		int suffixCounter = 0;
		int stackCounter = 0;
		int useCounter = 0;
		for(String assetIDInfo : assetIDs) {
			String[] arguments = assetIDInfo.split(INITIAL_DELIMITER, 1);
			int slotValue = Integer.parseInt(arguments[0]);
			String assetID = arguments[1];
			SlotType slotType = SlotType.getSlotType(slotValue);
			switch(slotType) {
			case ATTRIBUTE:
				if(attributeCounter == slotType.max()) {
					throw new ExcessAssetException("Too many attribute assets are trying to be placed on an Item.");
				}
				itemAssets.add(new DefinedAsset(this.attributeManager.getItemAsset(item, assetID), SlotType.ATTRIBUTE));
				attributeCounter++;
				break;
			case INCREMENTAL:
				if(incrementalCounter == slotType.max()) {
					throw new ExcessAssetException("Too many incremental assets are trying to be placed on an Item.");
				}
				itemAssets.add(new DefinedAsset(this.experienceManager.getItemAsset(item, assetID), SlotType.INCREMENTAL));
				incrementalCounter++;
				break;
			case PREFIX:
				if(prefixCounter == slotType.max()) {
					throw new ExcessAssetException("Too many prefix assets are trying to be placed on an Item.");
				}
				itemAssets.add(new DefinedAsset(this.statusManager.getItemAsset(item, assetID), SlotType.PREFIX));
				prefixCounter++;
				break;
			case SUFFIX:
				if(suffixCounter == slotType.max()) {
					throw new ExcessAssetException("Too many suffix assets are trying to be placed on an Item.");
				}
				itemAssets.add(new DefinedAsset(this.statusManager.getItemAsset(item, assetID), SlotType.SUFFIX));
				suffixCounter++;
				break;
			case STACK:
				if(stackCounter == slotType.max()) {
					throw new ExcessAssetException("Too many stack assets are trying to be placed on an Item.");
				}
				itemAssets.add(new DefinedAsset(this.stackManager.getItemAsset(item, assetID), SlotType.STACK));
				stackCounter++;
				break;
			case USE:
				if(useCounter == slotType.max()) {
					throw new ExcessAssetException("Too many use assets are trying to be placed on an Item.");
				}
				itemAssets.add(new DefinedAsset(this.useManager.getItemAsset(item, assetID), SlotType.USE));
				useCounter++;
				break;
			default:
				throw new NoSuchElementException();			
			}
		}
		return itemAssets;
	}
	
}
