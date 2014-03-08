package com.codari.apicore.asset;

import com.codari.api5.asset.AssetType;

public interface AssetEntryFilter {
	public boolean allowRegistration(String registration);
	public boolean allowType(AssetType type);
	public boolean allowName(String name);
}
