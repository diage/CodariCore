package com.codari.arenacore.players.skills;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;

public class SkillListener implements Listener {
	@EventHandler()
	public void triggerDoubleJump(PlayerToggleFlightEvent e) {
		if(!(e.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
			e.getPlayer().setFlying(false);
			Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
			((CombatantCore)combatant).doubleJump(combatant);
		}
	}

	@EventHandler()
	public void triggerBlock(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if(this.checkIfWeapon(e.getItem().getType())) {
				Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
				((CombatantCore)combatant).block(combatant);				
			}
		}
	}

	@EventHandler()
	public void triggerSprint(PlayerToggleSprintEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		((CombatantCore)combatant).sprint(combatant);
	}
	
	@EventHandler()
	public void playerMoveEvent(PlayerMoveEvent e) {
		if(e.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			e.getPlayer().setWalkSpeed(e.getPlayer().isSprinting() ? 0.2f : 0.2f);
		}
	}

	@EventHandler()
	public void triggerSneak(PlayerToggleSneakEvent e) {
		Combatant combatant = Codari.getArenaManager().getCombatant(e.getPlayer());
		((CombatantCore)combatant).sneak(combatant);
	}
	
	private boolean checkIfWeapon(Material material) {
		return EnchantmentTarget.WEAPON.includes(material) || EnchantmentTarget.BOW.includes(material);
	}
}
