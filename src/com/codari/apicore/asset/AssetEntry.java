package com.codari.apicore.asset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.codari.api5.asset.AssetRegistrationException;
import com.codari.apicore.CodariCore;

public final class AssetEntry {
	//-----Constants-----//
	private static final String ASSET_DESCRIPTION_FILENAME = "assets.txt";
	private static final Pattern ASSET_NAME_PATTERN = Pattern.compile("^[\\w-]+$");
	private static final Pattern CLASS_NAME_PATTERN =
			Pattern.compile("([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*");
	
	//-----Static Methods-----//
	public static List<AssetEntry> loadEntries(File file) throws AssetRegistrationException {
		List<AssetEntry> assetEntries = new ArrayList<>();
		try (JarFile jarFile = new JarFile(file)) {
			JarEntry jarEntry = jarFile.getJarEntry(ASSET_DESCRIPTION_FILENAME);
			if (jarEntry == null) {
				throw new AssetRegistrationException("could not find '" + ASSET_DESCRIPTION_FILENAME + "'" +
						" within " + file);
			}
			try (Scanner scanner = new Scanner(jarFile.getInputStream(jarEntry))) {
				for (int lineNumber = 1; scanner.hasNextLine(); lineNumber++) {
					String[] rawEntry = Arrays.copyOf(scanner.nextLine().split(":", 2), 2);
					AssetEntry assetEntry;
					try {
						assetEntry = new AssetEntry(file, lineNumber, rawEntry);
					} catch (AssetRegistrationException ex) {
						CodariCore.instance().getLogger().log(Level.WARNING, "Invalid asset entry", ex);
						continue;
					}
					assetEntries.add(assetEntry);
				}
			} catch (IOException ex) {
				throw new AssetRegistrationException("Failed to access '" + ASSET_DESCRIPTION_FILENAME + "'" +
						" within " + file, ex);
			}
		} catch (IOException ex) {
			throw new AssetRegistrationException("Failed to open " + file.getName(), ex);
		} catch (Exception ex) {
			if (ex instanceof AssetRegistrationException) {
				throw ex;
			}
			throw new AssetRegistrationException("Unknown error occured while accessing " + 
					"'" + ASSET_DESCRIPTION_FILENAME + "' within " + file.getName(), ex);
		}
		return assetEntries;
	}
	
	//-----Fields-----//
	private final File origin;
	private final int lineNumber;
	private final String assetName;
	private final String className;
	
	//-----Constructor-----//
	private AssetEntry(File origin, int lineNumber, String[] rawEntry) throws AssetRegistrationException {
		if (!ASSET_NAME_PATTERN.matcher(rawEntry[0]).matches()) {
			throw new AssetRegistrationException("Invalid asset name '" + rawEntry[0] + "'" + "found on line " +
					lineNumber + " of '" + ASSET_DESCRIPTION_FILENAME + "' within " + origin.getName());
		}
		if (rawEntry[0] == null) {
			throw new AssetRegistrationException("No class name defined on line " + lineNumber + " of " +
					"'" + ASSET_DESCRIPTION_FILENAME + "' within " + origin.getName());
		}
		if (!CLASS_NAME_PATTERN.matcher(rawEntry[0]).matches()) {
			throw new AssetRegistrationException("Invalid class name '" + rawEntry[1] + "'" + "found on line " +
					lineNumber + " of '" + ASSET_DESCRIPTION_FILENAME + "' within " + origin.getName());
		}
		this.origin = origin;
		this.lineNumber = lineNumber;
		this.assetName = rawEntry[0];
		this.className = rawEntry[1];
	}
	
	//-----Methods-----//
	public File getOrigin() {
		return this.origin;
	}
	
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	public String getAssetName() {
		return this.assetName;
	}
	
	public String getClassName() {
		return this.className;
	}
}
