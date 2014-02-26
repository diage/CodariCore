package com.codari.apicore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.api5.Codari;
import com.codari.api5.events.HotbarSelectEvent;
import com.codari.api5.util.scheduler.BukkitTime;

public class HotbarTest implements Listener {
	public HotbarTest() {
		Codari.getArenaManager().getCombatant("Soren_Endon").setHotbarActive(true);
		Codari.getArenaManager().getCombatant("Diage").setHotbarActive(true);
		Codari.getArenaManager().getCombatant("Soren025").setHotbarActive(true);
	}
	
	private final PotionEffect shield = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (int) BukkitTime.HOUR.tickUnit(), 125);
	private final PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, (int) BukkitTime.HOUR.tickUnit(), 125);
	private final PotionEffect damage = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (int) BukkitTime.HOUR.tickUnit(), 125);
	private final PotionEffect health = new PotionEffect(PotionEffectType.HEALTH_BOOST, (int) BukkitTime.HOUR.tickUnit(), 125);
	
	@EventHandler
	private void hotbarTest(HotbarSelectEvent e) {
		switch (e.getOption()) {
		case HOTBAR_1:
			e.getCombatant().getPlayer().addPotionEffect(damage);
			e.getCombatant().getPlayer().sendMessage(e.getOption().toString());
			e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
			break;
		case HOTBAR_2:
			e.getCombatant().getPlayer().addPotionEffect(shield);
			e.getCombatant().getPlayer().sendMessage(e.getOption().toString());
			e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
			break;
		case HOTBAR_3:
			e.getCombatant().getPlayer().addPotionEffect(regen);
			e.getCombatant().getPlayer().sendMessage(e.getOption().toString());
			e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
			break;
		case HOTBAR_4:
			e.getCombatant().getPlayer().addPotionEffect(health);
			e.getCombatant().getPlayer().sendMessage(e.getOption().toString());
			e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
			break;
		case HOTBAR_5:
			e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
			e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
			e.getCombatant().getPlayer().removePotionEffect(PotionEffectType.HEALTH_BOOST);
			e.getCombatant().getPlayer().sendMessage(e.getOption().toString());
			e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
			break;
		case HOTBAR_6:
			e.getCombatant().getPlayer().setOp(true);
			e.getCombatant().getPlayer().sendMessage(e.getOption().toString());
			e.getCombatant().getPlayer().sendMessage(e.getCombatant().getPlayer().getActivePotionEffects().toString());
			break;
		default:
			break;
		}
	}
}