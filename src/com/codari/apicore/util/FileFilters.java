package com.codari.apicore.util;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public enum FileFilters implements FileFilter {
	JAR {
		//-----Fields-----//
		private final Pattern jarPattern = Pattern.compile("^[\\w-]+\\.jar$");
		
		//-----Methods-----//
		@Override
		public boolean accept(File file) {
			return this.jarPattern.matcher(file.getName()).matches();
		}
	};
	
	//-----Methods-----//
	@Override
	public abstract boolean accept(File file);
}
