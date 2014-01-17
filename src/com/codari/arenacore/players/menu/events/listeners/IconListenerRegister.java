package com.codari.arenacore.players.menu.events.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.codari.arenacore.players.builders.BuildingListener;
import com.codari.arenacore.players.builders.kit.KitBuilderListener;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.builders.kit.ToolBarListener;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.GuildMenuListener;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.TeamMenuListener;

public class IconListenerRegister {
	
	public IconListenerRegister(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new ToolBarListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new IconListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new KitListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new KitBuilderListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new TeamMenuListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new GuildMenuListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new BuildingListener(), plugin);
	}
}
