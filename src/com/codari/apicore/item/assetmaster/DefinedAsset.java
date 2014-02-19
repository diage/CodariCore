package com.codari.apicore.item.assetmaster;

import com.codari.arena5.item.assets.ItemAsset;

public class DefinedAsset {
	private ItemAsset item;
	private SlotType slotType;
	
	public DefinedAsset(ItemAsset item, SlotType slotType) {
		this.item = item;
		this.slotType = slotType;
	}

	public ItemAsset getItem() {
		return this.item;
	}

	public SlotType getSlotType() {
		return this.slotType;
	}
	
}
