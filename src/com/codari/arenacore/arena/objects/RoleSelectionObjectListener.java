package com.codari.arenacore.arena.objects;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.apicore.CodariCore;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;

public class RoleSelectionObjectListener implements Listener {
	private static Map<String, RoleSelectionObject> roleSelectionObjects = new HashMap<>();

	@EventHandler()
	private void selectRoleSelectionObject(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock() instanceof RoleSelectionObject) {
				Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
				if(/*combatant.inArena()*/true) {	//FIXME - leaving this out for testing purposes
					RoleSelectionObject roleSelectionObject = ((RoleSelectionObject) e.getClickedBlock());
					roleSelectionObject.interact(combatant);
					roleSelectionObjects.put(e.getPlayer().getName(), roleSelectionObject);
				}
			}
		}
	} 

	@EventHandler() 
	private void selectRoleIcon(InventoryClickEvent e) {
		if(e.getInventory().getTitle().equals(RoleSelectionObject.INVENTORY_NAME)) {
			if(e.getWhoClicked() instanceof Player) {
				Player player = (Player) e.getWhoClicked();
				Combatant combatant = Codari.getArenaManager().getCombatant(player);
				String newRoleName = e.getCurrentItem().getItemMeta().getDisplayName();
				Role role = ((CodariCore) CodariI.INSTANCE).getRoleManager().getRole(newRoleName);
				if(role != null) {
					roleSelectionObjects.get(player.getName()).adjustRoleIcons(combatant, newRoleName);
					combatant.setRole(role);
					player.sendMessage(ChatColor.AQUA + "Your role is now " + newRoleName + ".");
				} else {
					Bukkit.broadcastMessage(ChatColor.RED + "COmbatant is trying to select arole but it's not working!"); //TODO - for testing
				}
			}
			e.setCancelled(true);
		}
	}
}
