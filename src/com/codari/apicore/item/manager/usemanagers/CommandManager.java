package com.codari.apicore.item.manager.usemanagers;

import java.util.HashMap;
import java.util.Map;

import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.Command;

public class CommandManager implements AssetManager<Command> {
	private final static String DELIMITER = "@";
	
	private Map<String, Command> commandAssets;
	
	public CommandManager() {
		this.commandAssets = new HashMap<>();
	}
	
	@Override
	public Command getItemAsset(final CodariItem item, final String assetID) throws Exception {
		String[] parsedString = assetID.split(DELIMITER, 1);
		if(this.commandAssets.containsKey(parsedString[0])) {
			return this.commandAssets.get(parsedString[0]);
		}
		//Return new Item Asset
		return null;
	}

}
