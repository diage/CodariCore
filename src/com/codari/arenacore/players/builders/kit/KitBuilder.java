package com.codari.arenacore.players.builders.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

import com.codari.arena5.rules.GameRule;
import com.codari.arenacore.rules.GameRuleCore;

public class KitBuilder implements Listener {
	private String name;
	private GameRule gameRule;
	
	public KitBuilder(String name) {
		this.name = name;
		this.gameRule = new GameRuleCore();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Kit buildKit(String kitName) {
		if(this.gameRule.isValid()) {
			return new Kit(kitName, this.gameRule);
		} else {
			Bukkit.broadcastMessage(ChatColor.DARK_RED + "Warning: GameRule not valid!");
			return null;
		}
	}
}
