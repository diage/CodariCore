package com.codari.arenacore.players.builders.kit;

import java.util.ArrayList;
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
import com.codari.arena5.objects.spawnable.ListenerFixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.hotbar.HotbarSelectEvent;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.arena.ArenaBuilderCore;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.arena.objects.RoleSelectionObject;
import com.codari.arenacore.arena.objects.SpawnPoint;
import com.codari.arenacore.players.builders.ToolbarManager;
import com.codari.arenacore.players.combatants.CombatantCore;

public class ToolBarListener implements Listener {
	@EventHandler
	public void toolSelect(HotbarSelectEvent e) {
		ToolbarManager toolbarManager = ((CombatantCore) e.getCombatant()).getToolbarManager();
		if (toolbarManager.isToolBarEnabled()) {
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
				toolbarManager.disableToolBar();
				break;
			default:
				break;
			}
		}
	}

	@EventHandler
	public void placeObject(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem() != null) {
			ToolbarManager toolbarManager = ((CombatantCore) Codari.getArenaManager().getCombatant(e.getPlayer())).getToolbarManager();
			if (toolbarManager.isToolBarEnabled()) {
				Kit kit = toolbarManager.getToolbarKit();
				ArenaBuilderCore builder = ((ArenaManagerCore) Codari.getArenaManager()).getArenaBuilder(kit.getName());
				Location location = e.getClickedBlock().getLocation();
				ArenaObject arenaObject;
				location.setY(location.getY() + 1);
				if (e.getItem().equals(kit.getTools()[4])) {
					String objectName = SpawnPoint.SPAWN_POINT_NAME;
					arenaObject = ((LibraryCore) Codari.getLibrary()).createObject(objectName, location);
					builder.addSpawnLocation((SpawnPoint) arenaObject);
					e.getPlayer().sendMessage(kit.addSpawn(e.getItem(), location));
					arenaObject.reveal();
					return;
				}
				String objectName = e.getItem().getItemMeta().getDisplayName();
				if(!objectName.equals(RoleSelectionObject.OBJECT_NAME)) {
					arenaObject = ((LibraryCore) Codari.getLibrary()).createObject(objectName, location);
				} else {
					arenaObject = new RoleSelectionObject(location, kit.getRoleDatas());
				}
				List<String> extraInformation = e.getItem().getItemMeta().hasLore() ? e.getItem().getItemMeta().getLore() : new ArrayList<String>();
				if(this.registerArenaObject(kit, arenaObject, extraInformation, builder)) {
					//After an Arena Object is added, all the roles have to be checked again to make sure they have the required links
					kit.checkIfRolesHaveRequiredLinks();
					arenaObject.reveal();
					e.getPlayer().sendMessage(ChatColor.GREEN + " Object Placed: " + arenaObject.getName());
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "Failed to register Arena Object!");
				}
			}
		}
	}	

	private boolean registerArenaObject(Kit kit, ArenaObject arenaObject, List<String> extraInformation, ArenaBuilderCore builder) {
		if(kit != null && arenaObject != null && builder != null) {
			//---Registering Arena Objects---//
			if(arenaObject instanceof RandomSpawnableObject) {
				builder.registerRandomSpawnable((RandomSpawnableObject) arenaObject, extraInformation.get(0));
				return true;
			} else if(arenaObject instanceof FixedSpawnableObject) {
				if(extraInformation.size() == 0) {
					builder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, Time.NULL);
				} else if(extraInformation.size() == 1) {
					builder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, new Time(0, 0, Long.parseLong(extraInformation.get(0))));
				} else if(extraInformation.size() >= 2) {
					builder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, new Time(0, 0, Long.parseLong(extraInformation.get(0))), new Time(0, 0, Long.parseLong(extraInformation.get(1))));
				}
				if(arenaObject instanceof ListenerFixedSpawnableObject) {
					((ListenerFixedSpawnableObject) arenaObject).setArenaName(kit.getName());	
				}
				return true;
			} else if(arenaObject instanceof ImmediatePersistentObject) {
				builder.registerPersistent((ImmediatePersistentObject) arenaObject);
				return true;
			} else if(arenaObject instanceof DelayedPersistentObject) {
				if(extraInformation.size() >= 2) {
					builder.registerPersistent((DelayedPersistentObject) arenaObject, new Time(0, 0, Long.parseLong(extraInformation.get(0))), Boolean.parseBoolean(extraInformation.get(1)));
				}
				return true;
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + "We are trying to place an object that isn't a random spawnable object "
						+ "/ fixed spawnable object / persistent object !"); //TODO - for testing
			}
		}  
		return false;
	}
}