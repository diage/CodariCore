package com.codari.apicore.item.asset.useassets;

import java.util.List;

import org.bukkit.Bukkit;

import com.codari.api5.events.itemevents.CodariItemUseEvent;
import com.codari.api5.events.itemevents.CodariItemUseEvent.UseType;
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
		Bukkit.getPluginManager().callEvent(new CodariItemUseEvent(this.item, UseType.COMMAND));
		this.command.issue(this.item.getCombatant());
	}

}
