package com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SaveKitIcon extends ExecutableIcon {

	public SaveKitIcon(Combatant combatant) {
		super(Material.REDSTONE_BLOCK, combatant, "Save Kit");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(KitListener.requestedKitNames.containsKey(player.getName())) {
			if(((CombatantCore)this.getCombatant()).getKitManager().getSelectedKitBuilder() != null) {
				String kitName = KitListener.requestedKitNames.get(player.getName());
				if(!((CombatantCore)this.getCombatant()).getKitManager().containsKit(kitName)) {
					if(((CombatantCore)this.getCombatant()).getKitManager().createKit(kitName)) {
						player.sendMessage(ChatColor.GREEN + "You have succesfully created a Kit!");
						for(Combatant combatant : ((ArenaManagerCore) Codari.getArenaManager()).getCombatants()) {	
							if(combatant != null) {
								((CombatantCore) combatant).getDynamicMenuManager().addKitIcon(combatant, kitName);
							}
						}
						KitListener.requestedKitNames.remove(player.getName());
					}
				} else {
					player.sendMessage(ChatColor.RED + "A Kit with the name \"" + kitName + "\" already exists!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You must select a Kit Builder first!");
			}	
		} else {
			player.sendMessage(ChatColor.RED + "You must enter a name for the Kit first!");
		}	
	}
}
