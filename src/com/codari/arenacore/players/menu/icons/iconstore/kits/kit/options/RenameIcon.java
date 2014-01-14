package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.RequestIcon;


public class RenameIcon extends RequestIcon implements Listener {
	private Combatant combatant;
	
	public RenameIcon(Combatant combatant) {
		super(Material.REDSTONE_BLOCK, combatant, "Rename");
		this.combatant = combatant;
	}

	@Override
	public String getConversationString() {
		return "Type in a new name for the kit.";
	}

	@EventHandler()
	private void renameKit(IconRequestEvent e) {
		if(e.getIcon() instanceof RenameIcon) {
			if(e.getIcon().getCombatant().equals(this.getCombatant())) {
				Kit kit = KitListener.getKit(this.getCombatant());
				((CombatantCore)this.combatant).getKitManager().renameKit(kit.getName(), e.getPlayerInput());
			}
		}
	}
}
