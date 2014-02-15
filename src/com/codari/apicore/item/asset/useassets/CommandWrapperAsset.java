package com.codari.apicore.item.asset.useassets;

import java.util.List;

import com.codari.arena5.item.CodariItem;
import com.codari.arena5.item.assets.Command;

public class CommandWrapperAsset implements UseAsset {
	private CodariItem item;
	private Command command;
	
	public CommandWrapperAsset(CodariItem item, Command command) {
		this.item = item;
		this.command = command;
	}
	
	@Override
	public String serializeToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void use() {
		this.command.issue(this.item.getCombatant());
	}

}
