package com.codari.arenacore.players.builders;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arenacore.arena.ArenaBuilderCore;
import com.codari.arenacore.arena.ArenaManagerCore;

public class BuildingListener implements Listener {
	private static Map<String, String> currentArenasBeingBuilt = new HashMap<>();

	@EventHandler()
	private void buildingStartEvent(BuildingStartEvent e) {
		Player player = e.getCombatant().getPlayer();
		String arenaBuilderName = e.getArenaBuilderName();
		if(!currentArenasBeingBuilt.containsValue(arenaBuilderName)) {
			ArenaBuilderCore arenaBuilder = ((ArenaManagerCore) Codari.getArenaManager()).getArenaBuilder(arenaBuilderName);
			for(ArenaObject arenaObject : arenaBuilder.getArenaObjectsCopyList()) {
				arenaObject.reveal();
			}
		}
		currentArenasBeingBuilt.put(player.getName(), arenaBuilderName);
	}

	@EventHandler()
	private void buildingEndEvent(BuildingEndEvent e) {
		Player player = e.getCombatant().getPlayer();
		if(currentArenasBeingBuilt.containsKey(player.getName())) {
			String arenaBuilderName = currentArenasBeingBuilt.get(player.getName());
			currentArenasBeingBuilt.remove(player.getName());
			if(!currentArenasBeingBuilt.containsValue(arenaBuilderName)) {
				ArenaBuilderCore arenaBuilder = ((ArenaManagerCore) Codari.getArenaManager()).getArenaBuilder(arenaBuilderName);
				if(arenaBuilder != null) {
					for(ArenaObject arenaObject : arenaBuilder.getArenaObjectsCopyList()) {
						arenaObject.hide();
					}
				}
			}
		}
	}
}
