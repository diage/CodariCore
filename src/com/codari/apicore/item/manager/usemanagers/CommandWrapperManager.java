package com.codari.apicore.item.manager.usemanagers;

import com.codari.apicore.item.asset.useassets.CommandWrapperAsset;
import com.codari.apicore.item.asset.useassets.UseAsset;
import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;

public class CommandWrapperManager implements AssetManager<UseAsset> {
	private CommandManager commandManager;
	
	public CommandWrapperManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}
	
	@Override
	public UseAsset getItemAsset(CodariItem item, String assetID) throws Exception {
		return new CommandWrapperAsset(item, this.commandManager.getItemAsset(item, assetID));
	}

}
