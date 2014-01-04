package com.codari.arenacore.players.menu.events.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.builders.kit.ToolBarListener;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.CurrentArenaObjectNameListener;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.SaveKitIconListener;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.SaveSpawnableGroupIconListener;

public class IconListenerRegister {
	
	public IconListenerRegister(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new ToolBarListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new IconListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new KitListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new SaveKitIconListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new SaveSpawnableGroupIconListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new CurrentArenaObjectNameListener(), plugin);
	}
}
