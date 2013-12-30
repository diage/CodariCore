package com.codari.arenacore.players.builders.kit;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.Codari;
import com.codari.arena5.players.hotbar.HotbarSelectEvent;
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
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			KitManager kitManager = ((CombatantCore) Codari.getArenaManager().getCombatant(e.getPlayer())).getKitManager();
			if (kitManager.isToolBarEnabled()) {
				Location location = e.getClickedBlock().getLocation();
				//TODO Need arena objects converted to accept location
			}
		}
	}
}