package com.codari.arenacore.players.menu.icons.iconstore.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.api5.CodariI;
import com.codari.apicore.CodariCore;
import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.roles.creation.SelectRoleName;
import com.codari.arenacore.players.menu.icons.iconstore.roles.selection.NewRoleIcon;
import com.codari.arenacore.players.role.RoleFactory;

public class RoleMenuListener implements Listener {
	public static Map<String, RoleFactory> currentRoleFactories = new HashMap<>();
	
	@EventHandler()
	private void selectNewRole(IconMenuClickEvent e) {
		if(e.getIcon() instanceof NewRoleIcon) {
			if(!currentRoleFactories.containsKey(e.getIcon().getPlayerName())) {
				currentRoleFactories.put(e.getIcon().getPlayerName(), new RoleFactory());
			}
		}
	}
	
	@EventHandler()
	private void selectRoleName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectRoleName) {
			if(!currentRoleFactories.containsKey(e.getIcon().getPlayerName())) {
				currentRoleFactories.put(e.getIcon().getPlayerName(), new RoleFactory());
			}
			if(!((CodariCore) CodariI.INSTANCE).getRoleManager().containsRole(e.getPlayerInput())) {
				currentRoleFactories.get(e.getIcon().getPlayerName()).setName(e.getPlayerInput());
			} else {
				e.getIcon().getCombatant().getPlayer().sendMessage(ChatColor.RED + "A role with that name already exists. "
						+ "Please choose another one.");
			}
		}
	}
	
	
}
