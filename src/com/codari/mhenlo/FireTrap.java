package com.codari.mhenlo;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.codari.mhenlo.structure.TemplateTrap;

public class FireTrap extends TemplateTrap {
	//-----Fields-----//
	private int numberOfFireTicks = 40;

	public FireTrap(Player player, double radius) {
		super(player, radius);
	}

	//-----Private Methods-----//
	/* Sets all targets on fire  */
	private void setTargetsOnFire(List<Entity> targets) {
		for(int i = 0; i < targets.size(); i++) {
			Player player = (Player)targets.get(i);
			player.setFireTicks(this.numberOfFireTicks);
		}		
	}	

	@Override
	public void trigger(List<Entity> targets) {
		this.setTargetsOnFire(targets);
	}
}
