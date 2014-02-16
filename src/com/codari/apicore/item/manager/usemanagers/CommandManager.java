package com.codari.apicore.item.manager.usemanagers;

import java.util.HashMap;
import java.util.Map;

import com.codari.api5.Codari;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.Command;
import com.codari.arenacore.LibraryCore;

public class CommandManager implements AssetManager<Command> {	
	private Map<String, Command> commandAssets;
	
	public CommandManager() {
		this.commandAssets = new HashMap<>();
	}
	
	@Override
	public Command getItemAsset(final CodariItem item, final String assetID) throws Exception {
		if(this.commandAssets.containsKey(assetID)) {
			return this.commandAssets.get(assetID);
		}
		//Return new Item Asset
		Command command = (Command) ((LibraryCore) Codari.getLibrary()).createItemAsset(assetID);
		this.commandAssets.put(assetID, command);
		return command;
	}

}
