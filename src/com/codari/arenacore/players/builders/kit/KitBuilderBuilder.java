package com.codari.arenacore.players.builders.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.events.IconSelectionEvent;
import com.codari.arenacore.players.menu.icons.structure.Icon;

public class KitBuilderBuilder implements Listener {
	private Map<String, List<Icon>> selectedBuilders;

	public KitBuilderBuilder() {
		this.selectedBuilders = new HashMap<>();
	}

	@EventHandler()
	private void getName(IconRequestEvent e) {
		if(true /*TODO will need to design and check if it is the correct icon type*/) {
			((CombatantCore)e.getIcon().getCombatant()).getKitManager().createKitBuilder(e.getPlayerInput());
			//TODO Will need to then create a new Icon which can be added to the KitBuilder page. 
		}
	}

	@EventHandler()
	private void selectKitBuilderIcon(IconSelectionEvent e) {
		String combatantName = e.getIcon().getCombatant().getPlayerReference().getName();
		if(true /*TODO check if it is the correct icon type*/) {
			List<Icon> selectedIcons;
			if(!this.selectedBuilders.containsKey(combatantName)) {
				this.selectedBuilders.put(combatantName, new ArrayList<Icon>());
			}
			selectedIcons = this.selectedBuilders.get(combatantName);
			if(e.isSelected()) {
				selectedIcons.add(e.getIcon());
			} else {
				selectedIcons.remove(e.getIcon()); //TODO Implement .equals()
			}
		}
	}
	
	public List<Icon> getSelectedIcons(Combatant combatant) { //TODO will be used for delete or other functions such as rename or use
		return this.selectedBuilders.get(combatant.getPlayerReference().getName());
	}
}
