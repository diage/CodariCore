package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.api5.Codari;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class ConstructIcon extends ExecutableIcon {
	private String arenaName;
	private ArenaBuilder arenaBuilder;
	
	public ConstructIcon(Combatant combatant, String arenaName) {
		super(Material.REDSTONE_BLOCK, combatant, "Construct");
		this.arenaName = arenaName;
		this.arenaBuilder = ((CombatantCore)combatant).getKitManager().getKit(arenaName).getArenaBuilder();
	}

	@Override
	public void click() {
		if(this.arenaName != null && this.arenaBuilder != null) {
			Codari.getArenaManager().buildArena(this.arenaName, arenaBuilder);			
			Bukkit.broadcastMessage("Finalized!");	//TODO
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "Something is wrong in the ConstructIcon class!"); //TODO - For Testing
		}
		
	}
}
