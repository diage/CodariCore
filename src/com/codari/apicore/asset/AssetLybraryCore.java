package com.codari.apicore.asset;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.codari.api5.asset.Asset;
import com.codari.api5.asset.AssetLybrary;
import com.codari.api5.asset.AssetRegistrationException;
import com.codari.api5.asset.AssetType;
import com.codari.apicore.CodariCore;

public final class AssetLybraryCore implements AssetLybrary {
	
	//-----Constants-----//
	private final String ASSET_REGISTRATION_FILENAME = "assets.info";
	private final Pattern ASSET_NAME_PATTERN = Pattern.compile("^[\\w -]+$");
	
	//-----Fields-----//
	private final File assetDirectory = new File(CodariCore.instance().getDataFolder(), "assets");
	private final String assetDescriptionFileName = "assets.info";
	private final EnumMap<AssetType, Map<String, Asset>> assets = new EnumMap<>(AssetType.class);
	private final AssetClassLoader assetClassLoader = new AssetClassLoader();
	
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
				assetEntries.addAll(this.loadAssetEntries(file));
			} catch (AssetRegistrationException ex) {
				//TODO log this better
				ex.printStackTrace();
				continue;
			}
		}
		
		for (AssetEntry assetEntry : assetEntries) {
			Class<? extends Asset> assetClass;
			try {
				assetClass = assetEntry.loadAssetClass();
			} catch (AssetRegistrationException ex) {
				//TODO log this better
				CodariCore.instance().getLogger().log(Level.WARNING, assetEntry.toString(), ex);
				continue;
			}
			//TODO do stuff
		}
	}
	
	//-----Helper Methods-----//
	private List<AssetEntry> loadAssetEntries(File file) throws AssetRegistrationException {
		try (JarFile jarFile = new JarFile(file)) {
			JarEntry jarEntry = jarFile.getJarEntry(assetDescriptionFileName);
			if (jarEntry == null) {
				throw new AssetRegistrationException("could not find '" + assetDescriptionFileName + "'" +
						" within " + file.getName());
			}
			try (Scanner scanner = new Scanner(jarFile.getInputStream(jarEntry))) {
				try {
					this.assetClassLoader.addURL(file.toURI().toURL());
				} catch (IOException ex) {
					throw new AssetRegistrationException("Failed to load " + file.getName(), ex);
				}
				List<AssetEntry> assetEntries = new ArrayList<>();
				for (int lineNumber = 1; scanner.hasNextLine(); lineNumber++) {
					String[] rawEntry = Arrays.copyOf(scanner.nextLine().split(":", 2), 2);
					assetEntries.add(new AssetEntry(file, lineNumber, rawEntry));
				}
				return assetEntries;
			} catch (IOException ex) {
				throw new AssetRegistrationException("Failed to access '" + assetDescriptionFileName + "'" +
						" within " + file.getName(), ex);
			}
		} catch (IOException ex) {
			throw new AssetRegistrationException("Failed to open " + file.getName(), ex);
		} catch (Exception ex) {
			throw new AssetRegistrationException("Unknown error occured while loading assets from " + file.getName(), ex);
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
	
	//-----Asset Entry-----//
	private final class AssetEntry {
		//-----Fields-----//
		private final File origin;
		private final int lineNumber;
		private final String assetName;
		private final String className;
		private Class<? extends Asset> assetClass;
		
		//-----Utility Fields-----//
		private final String toString;
		
		//-----Constructor-----//
		private AssetEntry(File origin, int lineNumber, String[] entry) throws ClassNotFoundException {
			this.origin = origin;
			this.lineNumber = lineNumber;
			this.assetName = entry[0];
			this.className = entry[1];
			
			//Utility
			this.toString = "AssetEntry - " + this.origin.getName() +
					"[" + this.assetName + ":" + this.className + "]{line: " + this.lineNumber + "}";
		}
		
		//-----Methods-----//
		private File getOrigin() {
			return this.origin;
		}
		
		private int getLineNumber() {
			return this.lineNumber;
		}
		
		private String getAssetName() {
			return this.assetName;
		}
		
		private boolean isValidAssetName() {
			return ASSET_NAME_PATTERN.matcher(this.assetName).matches();
		}
		
		private Class<? extends Asset> loadAssetClass() throws AssetRegistrationException {
			if (this.assetClass == null) {
				try {
					Class<?> clazz = Class.forName(this.className, true, assetClassLoader);
					this.assetClass = clazz.asSubclass(Asset.class);
				} catch (Exception ex) {
					throw new AssetRegistrationException(ex);
				}
			}
			return this.assetClass;
		}
		
		//-----Utility Methods-----//
		@Override
		public String toString() {
			return this.toString;
		}
	}
}
