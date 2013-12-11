package com.codari;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

import com.codari.api5.CodariI;
import com.codari.api5.util.PlayerReference;
import com.codari.api5.util.PluginUtils;
import com.codari.apicore.CodariCore;
import com.codari.arena.objects.traps.ExplosionTrap;
import com.codari.arena.objects.traps.FireTrap;
import com.codari.arena.objects.traps.PoisonSnareTrap;
import com.codari.arena.objects.traps.structure.Trap;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.players.combatants.Combatant;

@SuppressWarnings({ "unused", "deprecation" })
public class Debugger implements Listener {
	private Random random = new Random();
	private final int NUMBER_OF_TRAPS = 3;
	
	public static void debug() {
		Bukkit.getPluginManager().registerEvents(new Debugger(), CodariI.INSTANCE);
		//Bukkit.getPluginManager().registerEvents(new TrapListener(), Codari.INSTANCE);-
	}
	/*
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
				Trap trap;
				int nextRandom = this.random.nextInt(3);
				switch(nextRandom) {
				case 0:
					trap = new FireTrap(e.getPlayer(), 2);
					break;
				case 1:
					trap = new ExplosionTrap(e.getPlayer(), 2);
					break;
				case 2:
					trap = new PoisonSnareTrap(e.getPlayer(), 2);
					break;
				default:
					trap = null;
				}
				e.getPlayer().teleport(loc);
				if (trap != null) {
					trap.spawn();
					Bukkit.broadcastMessage(trap.getClass().getSimpleName());
				} else {
					Bukkit.broadcastMessage("NO TRAP");
				}
			}
		}
	}*/
}
