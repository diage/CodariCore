package com.codari.apicore.asset;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.codari.api5.asset.AssetDescriptionFile;
import com.codari.api5.asset.AssetLybrary;
import com.codari.api5.asset.InvalidAssetDescriptionFileException;
import com.codari.apicore.CodariCore;

public final class AssetLybraryCore implements AssetLybrary {
	//-----Constants-----//
	
	//-----Fields-----//
	private final File assetDirectory;
	private final URLClassLoader assetClassLoader;
	
	//-----Constructor-----//
	public AssetLybraryCore() {
		this.assetDirectory = new File(CodariCore.instance().getDataFolder(), "assets");
		this.assetClassLoader = null;
		FileFilter jarFilter = new FileFilter() {
			//-----Fields-----//
			private final Pattern jarPattern = Pattern.compile("\\w+\\.jar$");
			
			//-----Methods-----//
			@Override
			public boolean accept(File file) {
				return file.isFile() && this.jarPattern.matcher(file.getName()).matches();
			}
		};
		
		Map<String, File> assetFiles = new HashMap<>();
		Map<String, Set<String>> assets = new HashMap<>();
		
		for (File file : this.assetDirectory.listFiles(jarFilter)) {
			AssetDescriptionFile assetDescription = null;
			try {
				assetDescription = this.getAssetDescriptionFile(file);
			} catch (Exception ex) {
				CodariCore.instance().getLogger().log(Level.SEVERE, "Failed to load " +
						" asset description file from " + file, ex);
				continue;
			}
		}
	}
	
	//-----Methods-----//
	private AssetDescriptionFile getAssetDescriptionFile(File file)
			throws InvalidAssetDescriptionFileException, IOException {
		try (JarFile jarFile = new JarFile(file)) {
			JarEntry jarEntry = jarFile.getJarEntry("asset.yml");
			if (jarEntry == null) {
				throw new InvalidAssetDescriptionFileException("could not find 'asset.yml' in " + file);
			}
			try (InputStream jarEntryStream = jarFile.getInputStream(jarEntry)) {
				return AssetDescriptionFile.load(jarEntryStream);
			}
		}
	}
}
