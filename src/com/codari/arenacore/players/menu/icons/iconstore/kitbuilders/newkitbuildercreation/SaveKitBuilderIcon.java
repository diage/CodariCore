package com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.newkitbuildercreation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.SaveKitBuilderIconListener;

public class SaveKitBuilderIcon extends ExecutableIcon {

	public SaveKitBuilderIcon(Combatant combatant) {
		super(Material.REDSTONE_BLOCK, combatant, "Save Kit Builder");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(SaveKitBuilderIconListener.requestedKitBuilderNames.containsKey(player.getName())) {
			String kitBuilderName = SaveKitBuilderIconListener.requestedKitBuilderNames.get(player.getName());
			if(!((CombatantCore)this.getCombatant()).getKitManager().containsKitBuilder(kitBuilderName)) {
				if(((CombatantCore)this.getCombatant()).getKitManager().createKitBuilder(kitBuilderName)) {
					player.sendMessage(ChatColor.GREEN + "You have succesfully created a Kit Builder!");
					SaveKitBuilderIconListener.requestedKitBuilderNames.remove(player.getName());
					((CombatantCore)this.getCombatant()).getKitManager().addKitBuilderIcon(this.getCombatant(), kitBuilderName);
				}
			} else {
				player.sendMessage(ChatColor.RED + "A Kit Builder with the name \"" + kitBuilderName + "\" already exists!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for the Kit Builder first!");
		}
	}

}
