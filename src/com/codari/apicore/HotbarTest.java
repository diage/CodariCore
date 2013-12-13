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
				e.getCombatant().getPlayer().setOp(true);
				break;
			case FOUR:
				e.getCombatant().getPlayer().addPotionEffect(health);
				break;
			case ONE:
				e.getCombatant().getPlayer().addPotionEffect(damage);
				break;
			case SIX:
				e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
				e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.HEALTH_BOOST);
				break;
			case THREE:
				e.getCombatant().getPlayer().addPotionEffect(regen);
				break;
			case TWO:
				e.getCombatant().getPlayer().addPotionEffect(shield);
				break;
			}
		}
	}
}