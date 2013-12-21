package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.events.*;

public class KitBuilderListener implements Listener {
	private static Map<String, KitBuilder> currentKitBuilders = new HashMap<>();
	
	public static void changeKit(Combatant combatant, KitBuilder kitBuilder) {
		currentKitBuilders.put(combatant.getPlayerReference().getName(), kitBuilder);
	}
	
	@SuppressWarnings("unused")
	@EventHandler()
	private void changeGameTime(IconHoverUpdateEvent e) {
		KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kitBuilder == null)) {
			if(true /*check for minute icon*/) {
				kitBuilder.updateMinutes(e.getNewInput());
			} else if(true /*check for second icon*/) {
				kitBuilder.updateSeconds(e.getNewInput());
			} else if(true /*check for tick icon*/) {
				kitBuilder.updateTicks(e.getNewInput());
			}
		}
	}
}
