package com.codari.apicore.asset;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.org.apache.commons.lang3.reflect.FieldUtils;

import com.codari.api5.asset.Asset;
import com.codari.api5.asset.AssetLybrary;
import com.codari.apicore.CodariCore;
import com.codari.apicore.util.FileFilters;

public final class AssetLybraryCore implements AssetLybrary {
	//-----Fields-----//
	private final File assetDirectory = new File(CodariCore.instance().getDataFolder(), "assets");
	private final AssetClassLoader assetClassLoader = new AssetClassLoader();
	private final Map<String, Map<String, Map<String, Asset>>> assets = new HashMap<>();
	
	//-----Constructor-----//
	public AssetLybraryCore() {
		List<AssetEntryCore> assetEntries = new ArrayList<>();
		
		for (File file : this.assetDirectory.listFiles(FileFilters.JAR)) {
			List<AssetEntryCore> loadedEntries = AssetEntryCore.loadEntries(file);
			if (loadedEntries.isEmpty()) {
				throw new RuntimeException("no entries in " + file);
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
			try {
				Asset asset = assetEntry.getMain().newInstance();
				FieldUtils.writeDeclaredField(asset, "entry", assetEntry);
				Map<String, Map<String, Asset>> layer1 = this.assets.get(assetEntry.getRegistration());
				if (layer1 == null) {
					layer1 = new HashMap<>();
				}
				Map<String, Asset> layer2 = layer1.get(assetEntry.getType().toString());
				if (layer2 == null) {
					layer2 = new HashMap<>();
				}
				layer2.put(assetEntry.getName(), asset);
			} catch (InstantiationException | IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//-----Methods-----//
	public Asset getAsset(String registration, String type, String name) {
		Map<String, Map<String, Asset>> layer1 = this.assets.get(registration);
		if (layer1 == null) {
			return null;
		}
		Map<String, Asset> layer2 = layer1.get(type);
		if (layer2 == null) {
			return null;
		}
		return layer2.get(name);
	}
	
	public Asset getAsset(String fullName) {
		String[] splitName = fullName.split("\\.");
		return this.getAsset(splitName[0], splitName[1], splitName[2]);
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
