package com.codari.arenacore.players.menu.icons.iconstore.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.teams.edit.SelectPlayerIcon;
import com.codari.arenacore.players.teams.TeamCore;

public class SelectPlayerInviteIconListener implements Listener {
	public static Map<String, String> requestedSelectPlayerNames = new HashMap<>();
	
	@EventHandler()
	public void selectPlayer(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectPlayerIcon) {
			if(!((TeamCore)e.getIcon().getCombatant().getTeam()).checkIfInQueue()) {
				requestedSelectPlayerNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());
			} else {
				e.getIcon().getCombatant().getPlayer().sendMessage(ChatColor.RED + "You can't invite a player while your team "
						+ "is in a queue.");
			}
		}
	}
}
