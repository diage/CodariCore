package com.codari.arenacore.players.skills;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;

public class SkillListener implements Listener {
	@EventHandler()
	public void triggerDoubleJump(PlayerToggleFlightEvent e) {
		if(!(e.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
			e.getPlayer().setFlying(false);
			Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(e.getPlayer());
			combatant.getRole().doubleJump(combatant);
		}
	}

	@EventHandler()
	public void triggerBlock(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if(e.getPlayer().isBlocking()) {
				Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(e.getPlayer());
				combatant.getRole().block(combatant);				
			}
		}
	}

	@EventHandler()
	public void triggerSprint(PlayerToggleSprintEvent e) {
		e.getPlayer().setSprinting(false);
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(e.getPlayer());
		combatant.getRole().sprint(combatant);
	}

	@EventHandler()
	public void triggerSneak(PlayerToggleSneakEvent e) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(e.getPlayer());
		combatant.getRole().sneak(combatant);
	}
}
