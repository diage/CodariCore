package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.events.IconHoverUpdateEvent;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateFixedDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateFixedDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateFixedDelayTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateFixedRepeatMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateFixedRepeatSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateFixedRepeatTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdatePersistentDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdatePersistentDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdatePersistentDelayTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateRandomDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateRandomDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateRandomDelayTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateRandomRepeatMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateRandomRepeatSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.hovericons.UpdateRandomRepeatTicksIcon;

public class KitListener implements Listener {
	private static Map<String, Kit> currentKits = new HashMap<>();
	
	public static void changeKit(Combatant combatant, Kit kit) {
		currentKits.put(combatant.getPlayerReference().getName(), kit);
	}
	
	@EventHandler()
	private void changeRandomDelayTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateRandomDelayMinutesIcon) {
				kit.updateRandomDelayMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateRandomDelaySecondsIcon) {
				kit.updateRandomDelaySeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateRandomDelayTicksIcon) {
				kit.updateRandomDelayTicks(e.getNewInput());
			}
		}
	}
	
	@EventHandler()
	private void changeRandomRepeatTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateRandomRepeatMinutesIcon) {
				kit.updateRandomRepeatMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateRandomRepeatSecondsIcon) {
				kit.updateRandomRepeatSeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateRandomRepeatTicksIcon) {
				kit.updateRandomRepeatTicks(e.getNewInput());
			}
		}		
	}
	
	@EventHandler()
	private void changeFixedDelayTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateFixedDelayMinutesIcon) {
				kit.updateFixedDelayMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateFixedDelaySecondsIcon) {
				kit.updateFixedDelaySeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateFixedDelayTicksIcon) {
				kit.updateFixedDelayTicks(e.getNewInput());
			}
		}		
	}
	
	@EventHandler()
	private void changeFixedRepeatTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateFixedRepeatMinutesIcon) {
				kit.updateFixedRepeatMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateFixedRepeatSecondsIcon) {
				kit.updateFixedRepeatSeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateFixedRepeatTicksIcon) {
				kit.updateFixedRepeatTicks(e.getNewInput());
			}
		}		
	}
	
	@EventHandler()
	private void changePersistentDelayTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdatePersistentDelayMinutesIcon) {
				kit.updateFixedDelayMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdatePersistentDelaySecondsIcon) {
				kit.updateFixedDelaySeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdatePersistentDelayTicksIcon) {
				kit.updateFixedDelayTicks(e.getNewInput());
			}
		}		
	}
	
	@EventHandler()
	private void changePersistentBoolean() {
		
	}	
}
