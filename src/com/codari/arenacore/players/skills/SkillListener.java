package com.codari.arenacore.players.skills;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;

public class SkillListener implements Listener {
	@EventHandler()
	public void triggerDoubleJump(PlayerToggleFlightEvent e) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(e.getPlayer());
		@SuppressWarnings("unused")
		Role role = combatant.getRole();
	}
	
	//Gotta make my own event to check when a player blocks
	@EventHandler()
	public void triggerBlock(PlayerToggleFlightEvent e) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(e.getPlayer());
		@SuppressWarnings("unused")
		Role role = combatant.getRole();
	}
	
	@SuppressWarnings("unused")
	@EventHandler()
	public void triggerSprint(PlayerToggleSprintEvent e) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(e.getPlayer());
		Role role = combatant.getRole();
	}
	
	@EventHandler()
	public void triggerSneak(PlayerToggleSneakEvent e) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant(e.getPlayer());
		@SuppressWarnings("unused")
		Role role = combatant.getRole();
	}
}
