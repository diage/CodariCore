package com.codari.arenacore.players.skills;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;

public class SkillListener implements Listener {
	@EventHandler()
	public void triggerDoubleJump(PlayerToggleFlightEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		if(combatant.inArena()) {
			e.getPlayer().setFlying(false);
			((CombatantCore)combatant).doubleJump();
			e.setCancelled(true);
		}
	}
}
