package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class SelectNameIcon extends RequestIcon implements Listener {
	private Kit kit;
	
	public SelectNameIcon(Combatant combatant, Kit kit) {
		super(Material.OBSIDIAN, combatant, "Select Name");
		this.kit = kit;
	}

	@Override
	public String getConversationString() {
		return "Select a name for this random spawnable group.";
	}

	@EventHandler()
	private void createGroupName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectNameIcon) {
			if(e.getIcon().getCombatant().equals(this.getCombatant())) {
				this.kit.createGroupName(e.getPlayerInput());
			}
		}
	}
}
