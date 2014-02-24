package com.codari.apicore.asset;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.org.apache.commons.lang3.StringUtils;

import com.codari.api5.asset.AssetLybrary;
import com.codari.apicore.CodariCore;

public final class AssetLybraryCore implements AssetLybrary {
	//-----Constants-----//
	
	//-----Fields-----//
	private final File assetDirectory;
	
	//-----Constructor-----//
	public AssetLybraryCore() {
		this.assetDirectory = new File(CodariCore.instance().getDataFolder(), "assets");
	}
	
	//-----Methods-----//
}
