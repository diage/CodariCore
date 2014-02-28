package com.codari.apicore.asset;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.codari.api5.asset.Asset;
import com.codari.api5.asset.AssetLybrary;
import com.codari.api5.asset.AssetRegistrationException;
import com.codari.apicore.CodariCore;

public final class AssetLybraryCore implements AssetLybrary {
	//-----Fields-----//
	private final File assetDirectory = new File(CodariCore.instance().getDataFolder(), "assets");
	private final AssetClassLoader assetClassLoader = new AssetClassLoader();
	private final Map<String, Asset> assets = new HashMap<>();
	
	//-----Constructor-----//
	public AssetLybraryCore() {
		FileFilter jarFilter = new FileFilter() {
			//-----Fields-----//
			private final Pattern jarPattern = Pattern.compile("^[\\w-]+\\.jar$");
			
			//-----Methods-----//
			@Override
			public boolean accept(File file) {
				return this.jarPattern.matcher(file.getName()).matches();
			}
		};
		
		List<AssetEntry> assetEntries = new ArrayList<>();
		
		for (File file : this.assetDirectory.listFiles(jarFilter)) {
			try {
				List<AssetEntry> loadedEntries = AssetEntry.loadEntries(file);
				if (loadedEntries.isEmpty()) {
					//TODO LOGGING
					CodariCore.instance().getLogger().log(Level.INFO, "No valid asset entries found in " +
							file.getName() + ", ignoring it");
					continue;
				}
				try {
					this.assetClassLoader.addURL(file.toURI().toURL());
				} catch (MalformedURLException ex) {
					throw new AssetRegistrationException(ex);
				}
				assetEntries.addAll(loadedEntries);
				
			} catch (AssetRegistrationException ex) {
				//TODO LOGGING
				CodariCore.instance().getLogger().log(Level.WARNING, "An error occured while loading assets " +
						" from " + file.getName(), ex);
			}
		}
		
		for (AssetEntry assetEntry : assetEntries) {
			//How to load class with custom class loader
			//Class.forName(assetEntry.getClassName(), true, this.assetClassLoader);
		}
	}
	
	//-----Methods-----//
	
	//-----Asset Class Loader-----//
	private final class AssetClassLoader extends URLClassLoader {
		//-----Constructor-----//
		private AssetClassLoader() {
			super(new URL[]{});
		}
		
		//-----Methods-----//
		@Override
		protected void addURL(URL url) {
			super.addURL(url);
		}
	}
}
