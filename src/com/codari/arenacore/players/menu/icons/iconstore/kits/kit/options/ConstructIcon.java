package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.ArenaCore;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.KitIcon;

public class ConstructIcon extends ExecutableIcon implements Listener {
	private String arenaName;
	private ArenaBuilder arenaBuilder;
	//private Combatant combatant;
	
	public ConstructIcon(Combatant combatant) {
		super(Material.REDSTONE_BLOCK, combatant, "Construct");
		//this.combatant = combatant;
	}

	@Override
	public void click() {
		if(this.arenaName != null && this.arenaBuilder != null) {
			//Copied from Finalize Command - Doublecheck this
			Arena arena = Codari.getArenaManager().buildArena(this.arenaName, arenaBuilder);
			File file = new File(CodariI.INSTANCE.getDataFolder(), this.arenaName + ".dat");
			CodariI.INSTANCE.getDataFolder().mkdirs();
			((ArenaCore) arena).serializeTest(file);
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "Something is wrong in the ConstructIcon class!"); //TODO - For Testing
		}
		
	}
	
	@EventHandler()
	private void kitSelection(IconMenuClickEvent e) {
		if(e.getIcon() instanceof KitIcon) {
			if(true/*this.combatant.equals(e.getCombatantWhoClicked())*/) {
				this.arenaName = ((KitIcon)e.getIcon()).getName();
				this.arenaBuilder = ((ArenaManagerCore)Codari.getArenaManager()).getArenaBuilder(this.arenaName);
			}
		}	
	}
}
