package com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SaveKitIcon extends ExecutableIcon implements Listener {
	private String kitName = "";
	
	public SaveKitIcon(Combatant combatant) {
		super(Material.REDSTONE_BLOCK, combatant, "Save Kit");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(!kitName.equals("")) {
			if(!((CombatantCore)this.getCombatant()).getKitManager().containsKit(kitName)) {
				((CombatantCore)this.getCombatant()).getKitManager().createKit(kitName);
				player.sendMessage(ChatColor.GREEN + "You have succesfully created a kit!");
			} else {
				player.sendMessage(ChatColor.RED + "A kit with the name \"" + kitName + "\" already exists!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for the kit first!");
		}	
	}

	@EventHandler()
	public void setKitName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectKitNameIcon) {
			if(e.getIcon().getCombatant().equals(this.getCombatant())) {
				this.kitName = e.getPlayerInput();
			}
		}
	}
}
