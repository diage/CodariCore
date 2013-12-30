package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.RequestIcon;

public class OverrideIcon extends RequestIcon implements Listener {
	private Combatant combatant;
	private String kitName;
	
	public OverrideIcon(Material material, Combatant combatant, String kitName) {
		super(material, combatant, "Set Override");
		this.combatant = combatant;
		this.kitName = kitName;
	}

	@Override
	public String getConversationString() {
		return "Type in true or false for the override.";
	}

	@EventHandler()
	private void setOverride(IconRequestEvent e) {
		if(e.getIcon() instanceof OverrideIcon) {
			if(this.getCombatant().equals(e.getIcon().getCombatant())) {
				((CombatantCore)this.combatant).getKitManager().selectKit(this.kitName).setOverride(Boolean.parseBoolean(e.getPlayerInput()));
			}
		}
	}
}
