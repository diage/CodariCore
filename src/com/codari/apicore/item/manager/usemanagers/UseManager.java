package com.codari.apicore.item.manager.usemanagers;

import com.codari.apicore.item.manager.interfaces.AssetManager;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.ItemAsset;

public class UseManager implements AssetManager<ItemAsset> {	
	public final static int COMMAND = 0;
	public final static int SPELL = 1;
	private final static String DELIMITER = "|";
	
	private CommandManager commandManager;
	private SpellManager spellManager;
	
	public UseManager() {
		this.commandManager = new CommandManager();
		this.spellManager = new SpellManager();
	}
	
	@Override
	public ItemAsset getItemAsset(CodariItem item, String assetID) throws Exception {
		String[] parsedString = assetID.split(DELIMITER, 1);
		if(parsedString.length != 2) {
			throw new IllegalArgumentException();
		}
		int managerType = Integer.valueOf(parsedString[0]);
		switch(managerType) {
		case COMMAND:
			return this.commandManager.getItemAsset(item, parsedString[1]);
		case SPELL:
			return this.spellManager.getItemAsset(item, parsedString[1]);
		default:
			throw new IllegalArgumentException();
		}
	}

}
