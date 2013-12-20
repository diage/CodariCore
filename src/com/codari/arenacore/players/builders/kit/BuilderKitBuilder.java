package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;

import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.rules.roledelegation.RoleDeclaration;
import com.codari.arena5.rules.timedaction.TimedAction;
import com.codari.arena5.rules.wincondition.WinCondition;
import com.codari.arenacore.players.menu.icons.MenuIcon;
import com.codari.arenacore.players.menu.icons.SelectionIcon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.menus.icongroup.IconGroup;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

@SuppressWarnings("unused")
public class BuilderKitBuilder extends IconGroup implements Listener {
	
	
	private ArenaBuilder arenaBuilder;
	private Combatant combatant;
	private Map<String, Class<? extends ArenaObject>> objects;
	private Map<String, Class<? extends RoleDeclaration>> declarations;
	private Map<String, Class<? extends TimedAction>> actions;
	private Map<String, Class<? extends WinCondition>> conditions;
	
	public BuilderKitBuilder(Combatant combatant) {
		//this.arenaBuilder = new ArenaBuilderCore();
		this.combatant = combatant;
		this.objects = new HashMap<>();
		this.declarations = new HashMap<>();
		this.actions = new HashMap<>();
		this.conditions = new HashMap<>();
	}
	
	public BuilderKitBuilder setArenaObjects(Map<String, Class<? extends ArenaObject>> objects) {
		this.objects = objects;
		return this;
	}

	public BuilderKitBuilder setRoleDeclarations(Map<String, Class<? extends RoleDeclaration>> declarations) {
		this.declarations = declarations;
		return this;
	}
	
	public BuilderKitBuilder setTimedActions(Map<String, Class<? extends TimedAction>> actions) {
		this.actions = actions;
		return this;
	}
	
	public BuilderKitBuilder setWinConditions(Map<String, Class<? extends WinCondition>> conditions) {
		this.conditions = conditions;
		return this;
	}
	
	public BuilderKit build() {
		this.buildArenaObjectGroup();
		
		return null;
	}
	
	private IconGroup buildArenaObjectGroup() {
		IconGroup iconGroup = new IconGroup();
		FunctionMenu functionMenu = new FunctionMenu();
		FunctionMenuSlot functionMenuSlot[] = FunctionMenuSlot.values();
		int itterator = 0;
		for(String string : this.objects.keySet()) {
			FunctionMenuSlot newSlot = functionMenuSlot[itterator];
			if(newSlot.equals(FunctionMenuSlot.C_ONE)) {
				itterator++;
				newSlot = functionMenuSlot[itterator];
			}
			if(functionMenu.size() == 13) {
				FunctionMenu nextMenu = new FunctionMenu();
				MenuIcon previous, next;
				
				next = new MenuIcon(Material.BOOK, this.combatant, nextMenu, "Next");
				if(iconGroup.hasPreviousPage()) {
					previous = new MenuIcon(Material.BOOK, this.combatant, (FunctionMenu) iconGroup.lookPreviousPage(), "Previous");
				} else {
					previous = new MenuIcon(Material.BOOK, this.combatant, ChatColor.GRAY + "Previous");
				}
				
				functionMenu.setSlot(FunctionMenuSlot.C_ONE, previous);
				functionMenu.setSlot(FunctionMenuSlot.C_FIVE, next);
				iconGroup.addPage(functionMenu);
				
				functionMenu = nextMenu;
				itterator = 0;
			}
			functionMenu.setSlot(newSlot, new SelectionIcon(Material.REDSTONE_BLOCK, this.combatant, string));
			itterator++;
		}
		
		return iconGroup;
	}
}
