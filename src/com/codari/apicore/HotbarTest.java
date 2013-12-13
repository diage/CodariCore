package com.codari.apicore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.api5.Codari;
import com.codari.api5.util.scheduler.BukkitTime;
import com.codari.arena5.players.hotbar.HotbarSelectEvent;

public class HotbarTest implements Listener {
	public HotbarTest() {
		Codari.getArenaManager().getCombatant("Soren_Endon").setHotbarActibe(true);
	}
	
	private final PotionEffect shield = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (int) BukkitTime.HOUR.tickUnit(), 125);
	private final PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, (int) BukkitTime.HOUR.tickUnit(), 125);
	private final PotionEffect damage = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) BukkitTime.HOUR.tickUnit(), 125);
	private final PotionEffect health = new PotionEffect(PotionEffectType.HEALTH_BOOST, (int) BukkitTime.HOUR.tickUnit(), 125);
	
	@EventHandler
	private void hotbarTest(HotbarSelectEvent e) {
		if (e.getCombatant().getPlayerReference().getName().equalsIgnoreCase("Soren_Endon")) {
			switch (e.getSlot()) {
			case FIVE:
				e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
				e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.HEALTH_BOOST);
				e.getCombatant().getPlayer().sendMessage(e.getSlot().toString());
				e.getCombatant().getPlayer().sendMessage("" + e.getCombatant().getPlayer().isOp());
				e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
				break;
			case FOUR:
				e.getCombatant().getPlayer().addPotionEffect(health);
				e.getCombatant().getPlayer().sendMessage(e.getSlot().toString());
				e.getCombatant().getPlayer().sendMessage("" + e.getCombatant().getPlayer().isOp());
				e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
				break;
			case ONE:
				e.getCombatant().getPlayer().addPotionEffect(damage);
				e.getCombatant().getPlayer().sendMessage(e.getSlot().toString());
				e.getCombatant().getPlayer().sendMessage("" + e.getCombatant().getPlayer().isOp());
				e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
				break;
			case SIX:
				e.getCombatant().getPlayer().setOp(true);
				e.getCombatant().getPlayer().sendMessage(e.getSlot().toString());
				e.getCombatant().getPlayer().sendMessage("" + e.getCombatant().getPlayer().isOp());
				e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
				break;
			case THREE:
				e.getCombatant().getPlayer().addPotionEffect(regen);
				e.getCombatant().getPlayer().sendMessage(e.getSlot().toString());
				e.getCombatant().getPlayer().sendMessage("" + e.getCombatant().getPlayer().isOp());
				e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
				break;
			case TWO:
				e.getCombatant().getPlayer().addPotionEffect(shield);
				e.getCombatant().getPlayer().sendMessage(e.getSlot().toString());
				e.getCombatant().getPlayer().sendMessage("" + e.getCombatant().getPlayer().isOp());
				e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
				break;
			}
		}
	}
}