package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SaveIcon extends ExecutableIcon {
	
	public SaveIcon(Combatant combatant) {
		super(Material.OBSIDIAN, combatant, "Save");
	}

	@Override
	public void click() {
		Kit kit = KitListener.getKit(this.getCombatant());
		if(kit != null) {
			kit.createFixedDelayTime();
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "Kit is null!");	//TODO
		}
	}
}
