package com.codari;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

import com.codari.api.Codari;
import com.codari.api.io.CodariIO;
import com.codari.api.util.PluginUtils;
import com.codari.mhenlo.FireTrap;
import com.codari.mhenlo.listeners.FireTrapListener;

@SuppressWarnings("unused")
public class Debugger implements Listener {
	public static void debug() {
		Bukkit.getPluginManager().registerEvents(new Debugger(), Codari.INSTANCE);
		Bukkit.getPluginManager().registerEvents(new FireTrapListener(), Codari.INSTANCE);
	}
	
	@EventHandler
	public void testerbugrer(PlayerInteractEvent e) {
		if (e.getItem() != null && e.getItem().getType() == Material.STICK) {
			Location loc = e.getPlayer().getLocation();
			BlockIterator i = new BlockIterator(e.getPlayer(), 100);
			Block target = null;
			while (i.hasNext()) {
				Block next = i.next();
				if (next.getType() != Material.AIR) {
					break;
				} else {
					target = next;
				}
			}
			if (target != null) {
				e.getPlayer().teleport(target.getLocation());
				FireTrap trap = new FireTrap(e.getPlayer(), 5);
				e.getPlayer().teleport(loc);
				trap.spawn();
			}
		}
	}
}