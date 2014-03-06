package com.codari.apicore.item.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.apicore.CodariCore;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.players.combatants.Combatant;

public class ItemUseEventListener implements Listener {

	@EventHandler()
	private void itemUseEvent(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getItemInHand() != null) {
			Combatant combatant = Codari.getArenaManager().getCombatant(player);
			ItemStack item = player.getItemInHand();
			CodariItem codariItem = ((CodariCore) CodariI.INSTANCE).getItemDataManager().getItem(combatant, item);
			if(codariItem != null) {
				codariItem.use();
			}
		}
	}
	
}
