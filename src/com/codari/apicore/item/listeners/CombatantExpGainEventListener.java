package com.codari.apicore.item.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.apicore.CodariCore;
import com.codari.arena5.item.CodariItem;
import com.codari.arena5.players.combatants.Combatant;

public class CombatantExpGainEventListener implements Listener {

	@EventHandler()
	private void experienceIncreaseEvent(PlayerExpChangeEvent e) {
		Player player = e.getPlayer();
		if(player.getItemInHand() != null && e.getAmount() > 0) {
			Combatant combatant = Codari.getArenaManager().getCombatant(player);
			CodariItem item = ((CodariCore) CodariI.INSTANCE).getItemDataManager().getItem(combatant, player.getItemInHand());
			item.addExp(e.getAmount());
			//Cancelling the Bukkit Event,
			e.setAmount(0);
		}
	}
	
}
