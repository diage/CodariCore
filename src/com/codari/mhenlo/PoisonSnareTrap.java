package com.codari.mhenlo;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.codari.mhenlo.structure.TemplateTrap;

public class PoisonSnareTrap extends TemplateTrap{
	//-----Fields-----//
	private int slowEffectDuration = 100;
	private int slowEffectAmplifier = 10;
	PotionEffect potionEffectSlow = new PotionEffect(PotionEffectType.SLOW, slowEffectDuration, slowEffectAmplifier);
	
	private int poisonEffectDuration = 100;
	private int poisonEffectAmplifier = 10;
	PotionEffect potionEffectPoison = new PotionEffect(PotionEffectType.POISON, poisonEffectDuration, poisonEffectAmplifier);

	public PoisonSnareTrap(Player player, double radius) {
		super(player, radius);
		super.clayStoneMetaDataValue = 5;
	}

	@Override
	public void trigger(List<Entity> targets) {
		for(Entity triggerTarget: targets) {
			potionEffectSlow.apply((LivingEntity) triggerTarget);
			potionEffectPoison.apply((LivingEntity) triggerTarget);
		}	
	}
}
