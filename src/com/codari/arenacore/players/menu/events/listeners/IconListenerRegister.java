package com.codari.arenacore.players.menu.events.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.codari.arenacore.players.builders.kit.ToolBarListener;

public class IconListenerRegister {
	
	public IconListenerRegister(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new ToolBarListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new IconListener(), plugin);
	}
}
