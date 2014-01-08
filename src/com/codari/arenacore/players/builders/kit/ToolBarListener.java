package com.codari.arenacore.players.builders.kit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.Codari;
import com.codari.api5.util.Time;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.DelayedPersistentObject;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.hotbar.HotbarSelectEvent;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.arena.ArenaBuilderCore;
import com.codari.arenacore.players.combatants.CombatantCore;

public class ToolBarListener implements Listener {
	@EventHandler
	public void toolSelect(HotbarSelectEvent e) {
		KitManager kitManager = ((CombatantCore) e.getCombatant()).getKitManager();
		if (kitManager.isToolBarEnabled()) {
			switch (e.getOption()) {
			case HOTBAR_1:
			case HOTBAR_2:
			case HOTBAR_3:
			case HOTBAR_4:
			case HOTBAR_5:
				ItemStack item = e.getItem();
				e.getCombatant().getPlayer().getInventory().setItem(7, item);
				break;
			case HOTBAR_6:
				kitManager.disableToolBar();
				break;
			default:
				break;
			}
		}
	}
	
	@EventHandler
	public void placeObject(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null) {
			KitManager kitManager = ((CombatantCore) Codari.getArenaManager().getCombatant(e.getPlayer())).getKitManager();
			if (kitManager.isToolBarEnabled()) {
				Kit kit = kitManager.getToolbarKit();
				ArenaBuilderCore builder = kit.getArenaBuilder();
				if (e.getItem().equals(kit.getTools()[4])) {
					Location location = e.getClickedBlock().getLocation();
					builder.addSpawnLocation(location);
					kit.addSpawn(location);
					kit.addSpawn(location);
					e.getPlayer().sendMessage(kit.spawnString(location));
					return;
				}
				String objectName = e.getItem().getItemMeta().getDisplayName();
				Location location = e.getClickedBlock().getLocation();
				ArenaObject arenaObject = ((LibraryCore) Codari.getLibrary()).createObject(objectName, location);
				List<String> extraInformation = e.getItem().getItemMeta().getLore();
				
				//---Registering Arena Objects---//
				if(arenaObject instanceof RandomSpawnableObject) {
					builder.registerRandomSpawnable((RandomSpawnableObject) arenaObject, extraInformation.get(0));
					e.getPlayer().sendMessage(ChatColor.GREEN + " Object Placed: " + arenaObject.getName());
				} else if(arenaObject instanceof FixedSpawnableObject) {
					if (extraInformation != null) {
						if(extraInformation.size() == 1) {
							builder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, new Time(0, 0, Long.parseLong(extraInformation.get(0))));
							e.getPlayer().sendMessage(ChatColor.GREEN + " Object Placed: " + arenaObject.getName());
						} else if(extraInformation.size() >= 2) {
							builder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, new Time(0, 0, Long.parseLong(extraInformation.get(0))), new Time(0, 0, Long.parseLong(extraInformation.get(1))));
							e.getPlayer().sendMessage(ChatColor.GREEN + " Object Placed: " + arenaObject.getName());
						}
					}
				} else if(arenaObject instanceof ImmediatePersistentObject) {
					builder.registerPersistent((ImmediatePersistentObject) arenaObject);
					e.getPlayer().sendMessage(ChatColor.GREEN + " Object Placed: " + arenaObject.getName());
				} else if(arenaObject instanceof DelayedPersistentObject) {
					if(extraInformation != null && extraInformation.size() >= 2) {
						builder.registerPersistent((DelayedPersistentObject) arenaObject, new Time(0, 0, Long.parseLong(extraInformation.get(0))), Boolean.parseBoolean(extraInformation.get(1)));
						e.getPlayer().sendMessage(ChatColor.GREEN + " Object Placed: " + arenaObject.getName());
					}
				} else {
					Bukkit.broadcastMessage(ChatColor.RED + "We are trying to place an object that isn't a random spawnable object "
							+ "/ fixed spawnable object / persistent object !"); //TODO - for testing
				}
			}
		}
	}
}