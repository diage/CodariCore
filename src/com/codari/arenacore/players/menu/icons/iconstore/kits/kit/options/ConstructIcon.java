package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.api5.Codari;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class ConstructIcon extends ExecutableIcon {
	private String arenaName;
	
	public ConstructIcon(Combatant combatant, String arenaName) {
		super(Material.REDSTONE_BLOCK, combatant, "Construct");
		this.arenaName = arenaName;
	}

	@Override
	public void click() {
		ArenaBuilder arenaBuilder = ((ArenaManagerCore)Codari.getArenaManager()).getArenaBuilder(this.arenaName);
		if(this.arenaName != null && arenaBuilder != null) {
			Codari.getArenaManager().buildArena(this.arenaName, arenaBuilder);			
			Bukkit.broadcastMessage("Finalized!");	//TODO
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "Something is wrong in the ConstructIcon class!"); //TODO - For Testing
		}
		
	}
}
