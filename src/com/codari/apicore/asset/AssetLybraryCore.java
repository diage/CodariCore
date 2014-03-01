package com.codari.apicore.asset;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.codari.api5.asset.AssetLybrary;
import com.codari.api5.asset.AssetRegistrationException;
import com.codari.apicore.CodariCore;

public final class AssetLybraryCore implements AssetLybrary {
	//-----Fields-----//
	private final File assetDirectory = new File(CodariCore.instance().getDataFolder(), "assets");
	private final AssetClassLoader assetClassLoader = new AssetClassLoader();
	//private final Map<String, Asset> assets = new HashMap<>();
	
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
				this.assetClassLoader.addFile(file);
				for (AssetEntry loadedEntry : loadedEntries) {
					try {
						loadedEntry.initilize(this.assetClassLoader);
					} catch (AssetRegistrationException ex) {
						//TODO LOGGING <INSERT INFORMATIVE MESSAGE HERE>
						CodariCore.instance().getLogger().log(Level.WARNING, "<INSERT INFORMATIVE MESSAGE HERE>", ex);
						continue;
					}
					assetEntries.add(loadedEntry);
				}
			} catch (AssetRegistrationException ex) {
				//TODO LOGGING
				CodariCore.instance().getLogger().log(Level.WARNING, "An error occured while loading assets " +
						" from " + file.getName(), ex);
			}
			
			for (AssetEntry assetEntry : assetEntries) {
				//TODO conflict resolving
				assetEntry.getAssetClass();//Temp so no errors <.<
			}
		}
	}
	
	//-----Methods-----//
	
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
		
		private void addFile(File file) throws AssetRegistrationException {
			try {
				this.addURL(file.toURI().toURL());
			} catch (MalformedURLException ex) {
				throw new AssetRegistrationException(ex);
			}
		}
	}
}
