package com.codari.arenacore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerCommandEvent;

import com.codari.api5.CodariI;

public class AntiTroyListener implements Listener {
	public AntiTroyListener() {
		Bukkit.getScheduler().runTaskTimer(CodariI.INSTANCE, new Runnable() {
			@Override
			public void run() {
				Player p = Bukkit.getPlayer("Koltiron");
				if (p != null) {
					p.getInventory().clear();
				}
			}
		}, 1, 1);
	}
	@EventHandler()
	public void stopTroy(PlayerInteractEvent e) {
		if(e.getPlayer().getName().equalsIgnoreCase("Koltiron")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void stopTroy(PlayerCommandPreprocessEvent e) {
		if(e.getPlayer().getName().equalsIgnoreCase("Koltiron")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void stopTroy(ServerCommandEvent e) {
		e.setCommand("");
		e.getSender().sendMessage("COMMANDS DISABLED");
	}
}
