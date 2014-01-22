package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.api5.Codari;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class ConstructIcon extends ExecutableIcon {
	
	public ConstructIcon(Combatant combatant) {
		super(Material.SHEARS, combatant, "Construct");
	}

	@Override
	public void click() {
		String arenaName = KitListener.getKit(this.getCombatant()).getName();
		ArenaBuilder arenaBuilder = ((ArenaManagerCore)Codari.getArenaManager()).getArenaBuilder(arenaName);
		if(arenaName != null && arenaBuilder != null) {
			if(((ArenaManagerCore) Codari.getArenaManager()).hasAnExistingRole(arenaName)) {
				Codari.getArenaManager().buildArena(arenaName, arenaBuilder);			
				Bukkit.broadcastMessage("Finalized!");	//TODO
			} else {
				this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "Failed to create arena - the arena must have at least one role!"); 
			}
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "Something is wrong in the ConstructIcon class!"); //TODO - For Testing
		}
		
	}
}
