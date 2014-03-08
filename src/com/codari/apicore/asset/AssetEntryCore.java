package com.codari.apicore.asset;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import com.codari.api5.asset.Asset;
import com.codari.api5.asset.AssetEntry;
import com.codari.api5.asset.AssetType;
import com.codari.apicore.asset.AssetLybraryCore.AssetClassLoader;

public final class AssetEntryCore implements AssetEntry {
	//-----Constants-----//
	private static final Yaml YAML = new Yaml(new SafeConstructor());
	private static final String ASSET_REGISTRATION_FILENAME = "assets.yml";
	//private static final Pattern ASSET_NAME_PATTERN = Pattern.compile("^[\\w-]+$");
	//private static final Pattern CLASS_NAME_PATTERN =
			//Pattern.compile("^([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*$");
	
	//-----Static Methods-----//
	public static List<AssetEntryCore> loadEntries(File file) {
		List<AssetEntryCore> assetEntries = new ArrayList<>();
		try (JarFile jarFile = new JarFile(file)) {
			JarEntry jarEntry = jarFile.getJarEntry(ASSET_REGISTRATION_FILENAME);
			if (jarEntry == null) {
				throw new Exception("couldnt find " + ASSET_REGISTRATION_FILENAME);
			}
			try (InputStream stream = jarFile.getInputStream(jarEntry)) {
				Map<?,?> args = (Map<?, ?>) YAML.load(stream);
				String registration = (String) args.get("registration");
				Map<?, ?> innerArgs = (Map<?, ?>) args.get("assets");
				for (Entry<?,?> rawEntry : innerArgs.entrySet()) {
					try {
						String assetName = (String) rawEntry.getKey();
						Map<?, ?> assetArgs = (Map<?, ?>) rawEntry.getValue();
						assetEntries.add(new AssetEntryCore(registration, assetName, assetArgs));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return assetEntries;
	}
	
	//-----Fields-----//
	private final String registration;
	private final String name;
	private final String main;
	private final String version;
	private Class<? extends Asset> clazz;
	private AssetType type;
	
	//-----Constructor-----//
	private AssetEntryCore(String registration, String name, Map<?, ?> args) {
		this.registration = registration;
		this.name = name;
		this.main = (String) args.get("main");
		this.version = (String) args.get("version");
	}
	
	//-----Methods-----//
	public String getRegistration() {
		return this.registration;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Class<? extends Asset> getMain() {
		return this.clazz;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public AssetType getType() {
		return this.type;
	}
	
	void initilize(AssetClassLoader assetClassLoader) throws Exception {
		if (this.clazz == null) {
			Class<?> clazz = Class.forName(this.main, true, assetClassLoader);
			this.clazz = clazz.asSubclass(Asset.class);
			this.type = AssetType.getType(this.clazz);
			if (this.type == null) {
				throw new Exception("no type found for " + this.clazz);
			}
		}
	}

	@Override
	public String getFullName() {
		return this.registration + "." + this.type + "." + this.name;
	}
}
