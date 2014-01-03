package com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.SaveKitIconListener;

public class SaveKitIcon extends ExecutableIcon {
	
	public SaveKitIcon(Combatant combatant) {
		super(Material.REDSTONE_BLOCK, combatant, "Save Kit");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(SaveKitIconListener.requestedKitNames.containsKey(player.getName())) {
			String kitName = SaveKitIconListener.requestedKitNames.get(player.getName());
			if(!((CombatantCore)this.getCombatant()).getKitManager().containsKit(kitName)) {
				((CombatantCore)this.getCombatant()).getKitManager().createKit(kitName);
				player.sendMessage(ChatColor.GREEN + "You have succesfully created a kit!");
				SaveKitIconListener.requestedKitNames.remove(player.getName());
				((CombatantCore)this.getCombatant()).getKitManager().reloadKitSelectionMenu(this.getCombatant(), kitName);
			} else {
				player.sendMessage(ChatColor.RED + "A kit with the name \"" + kitName + "\" already exists!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for the kit first!");
		}	
	}
}
