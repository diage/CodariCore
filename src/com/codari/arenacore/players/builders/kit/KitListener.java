package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.events.IconHoverUpdateEvent;
import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelayTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.UpdateFixedDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.UpdateFixedDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.UpdateFixedDelayTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.UpdateFixedRepeatMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.UpdateFixedRepeatSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.UpdateFixedRepeatTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.OverrideIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.UpdatePersistentDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.UpdatePersistentDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.UpdatePersistentDelayTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.KitIcon;

public class KitListener implements Listener {
	private static Map<String, Kit> currentKits = new HashMap<>();
	
	public static void changeKit(Combatant combatant, Kit kit) {
		currentKits.put(combatant.getPlayerReference().getName(), kit);
	}
	
	@EventHandler()
	private void kitSelection(IconMenuClickEvent e) {
		if(e.getIcon() instanceof KitIcon) {
			CombatantCore combatant = (CombatantCore)e.getIcon().getCombatant();
			String displayName = ((KitIcon)e.getIcon()).getDisplayName();
			if(combatant != null && displayName != null) {
				changeKit(combatant, combatant.getKitManager().getKit(displayName)); 
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + "Something is wrong in KitListener!");	//TODO
			}
		}
	}
	
	@EventHandler()
	private void changeRandomDelayTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateRandomDelayMinutesIcon) {
				kit.updateRandomDelayMinutes(e.getNewInput());
				Bukkit.broadcastMessage(ChatColor.GREEN + "Updated Random Delay Minutes!");	//TODO
			} else if(e.getIcon() instanceof UpdateRandomDelaySecondsIcon) {
				kit.updateRandomDelaySeconds(e.getNewInput());
				Bukkit.broadcastMessage(ChatColor.GREEN + "Updated Random Delay Seconds!");
			} else if(e.getIcon() instanceof UpdateRandomDelayTicksIcon) {
				kit.updateRandomDelayTicks(e.getNewInput());
				Bukkit.broadcastMessage(ChatColor.GREEN + "Updated Random Delay Ticks!");
			}
		}
	}
	
	@EventHandler()
	private void changeRandomRepeatTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateRandomRepeatMinutesIcon) {
				kit.updateRandomRepeatMinutes(e.getNewInput());
				Bukkit.broadcastMessage(ChatColor.GREEN + "Updated Random Repeat Minutes!");	//TODO
			} else if(e.getIcon() instanceof UpdateRandomRepeatSecondsIcon) {
				kit.updateRandomRepeatSeconds(e.getNewInput());
				Bukkit.broadcastMessage(ChatColor.GREEN + "Updated Random Repeat Seconds!");	//TODO
			} else if(e.getIcon() instanceof UpdateRandomRepeatTicksIcon) {
				kit.updateRandomRepeatTicks(e.getNewInput());
				Bukkit.broadcastMessage(ChatColor.GREEN + "Updated Random Repeat Ticks!");	//TODO
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
	private void changePersistentBoolean(IconRequestEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(e.getIcon() instanceof OverrideIcon) {
			kit.setOverride(Boolean.parseBoolean(e.getPlayerInput()));
		}
	}	
}
