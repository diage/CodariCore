package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectNameIcon extends RequestIcon implements Listener {
	private String kitName;
	private Combatant combatant;
	
	public SelectNameIcon(Combatant combatant, String kitName) {
		super(Material.OBSIDIAN, combatant, "Select Name");
		this.kitName = kitName;
		this.combatant = combatant;
	}

	@Override
	public String getConversationString() {
		return "Select a name for this random spawnable group.";
	}

	@EventHandler()
	private void createGroupName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectNameIcon) {
			if(e.getIcon().getCombatant().equals(this.getCombatant())) {
				((CombatantCore)this.combatant).getKitManager().selectKit(this.kitName).createGroupName(e.getPlayerInput());
			}
		}
	}
}
