package com.codari.apicore.asset;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.org.apache.commons.lang3.reflect.ConstructorUtils;
import net.minecraft.util.org.apache.commons.lang3.reflect.FieldUtils;

import com.codari.api5.asset.Asset;
import com.codari.api5.asset.AssetLybrary;
import com.codari.apicore.CodariCore;
import com.codari.apicore.util.FileFilters;
import com.codari.arena5.item.assets.AssetType;

public final class AssetLybraryCore implements AssetLybrary {
	//-----Fields-----//
	private final File assetDirectory = new File(CodariCore.instance().getDataFolder(), "assets");
	private final AssetClassLoader assetClassLoader = new AssetClassLoader();
	private final Map<String, Map<String, Map<String, AssetEntryCore>>> assets = new HashMap<>();
	
	//-----Constructor-----//
	public AssetLybraryCore() {
		List<AssetEntryCore> assetEntries = new ArrayList<>();
		
		for (File file : this.assetDirectory.listFiles(FileFilters.JAR)) {
			List<AssetEntryCore> loadedEntries = AssetEntryCore.loadEntries(file);
			if (loadedEntries.isEmpty()) {
				continue;
			}
			this.assetClassLoader.addFile(file);
			for (AssetEntryCore assetEntry : loadedEntries) {
				try {
					assetEntry.initilize(this.assetClassLoader);
					assetEntries.add(assetEntry);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
		for (AssetEntryCore assetEntry : assetEntries) {
			Map<String, Map<String, AssetEntryCore>> layer1 = this.assets.get(assetEntry.getRegistration());
			if (layer1 == null) {
				layer1 = new HashMap<>();
			}
			Map<String, AssetEntryCore> layer2 = layer1.get(assetEntry.getType().toString());
			if (layer2 == null) {
				layer2 = new HashMap<>();
			}
			layer2.put(assetEntry.getName(), assetEntry);
		}
	}
	
	//-----Methods-----//
	public <T extends Asset> T buildAsset(String registration, String type, String name, Object... args) {
		Map<String, Map<String, AssetEntryCore>> layer1 = this.assets.get(registration);
		if (layer1 == null) {
			return null;
		}
		Map<String, AssetEntryCore> layer2 = layer1.get(type);
		if (layer2 == null) {
			return null;
		}
		AssetEntryCore entry = layer2.get(name);
		if (entry == null) {
			return null;
		}
		//Class<? extends Asset>
		//ConstructorUtils.invokeConstructor(arg0, arg1)
		return null;
	}
	
	public Asset buildAsset(String registration, AssetType type, String name, Object... args) {
		return this.buildAsset(registration, type.toString(), name, args);
	}
	
	public Asset getAsset(String fullName, Object... args) {
		String[] splitName = fullName.split("\\.");
		return this.buildAsset(splitName[0], splitName[1], splitName[2], args);
	}
	
	//-----Asset Class Loader-----//
	final class AssetClassLoader extends URLClassLoader {
		//-----Constructor-----//
		private AssetClassLoader() {
			super(new URL[]{});
		}
		
		//-----Methods-----//
		@Override
		protected void addURL(URL url) {
			super.addURL(url);
		}
		
		private void addFile(File file) {
			try {
				this.addURL(file.toURI().toURL());
			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
