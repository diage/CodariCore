package com.codari.arenacore.players.menu.menus;

import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.iconstore.BorderIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.ExitIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.GuildsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.HelpIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.KitBuildersIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.KitsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.RolesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.utilitymenu.TeamsIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.menustore.guilds.InitialGuildOptions;
import com.codari.arenacore.players.menu.menus.menustore.help.HelpMenu;
import com.codari.arenacore.players.menu.menus.menustore.kitbuilders.KitBuilderSelection;
import com.codari.arenacore.players.menu.menus.menustore.kits.KitSelection;
import com.codari.arenacore.players.menu.menus.menustore.roles.RoleSelection;
import com.codari.arenacore.players.menu.menus.menustore.teams.InitialTeamOptions;
import com.codari.arenacore.players.menu.slots.MenuSlot;
import com.codari.arenacore.players.menu.slots.UtilityMenuSlot;


public class UtilityMenu implements Menu {
	protected Map<UtilityMenuSlot, Icon> icons;
	
	public UtilityMenu(Combatant combatant) {
		this.icons = new HashMap<>();
		this.icons.put(UtilityMenuSlot.SEP_ONE, new BorderIcon(combatant));
		this.icons.put(UtilityMenuSlot.SEP_TWO, new BorderIcon(combatant));
		this.icons.put(UtilityMenuSlot.SEP_THREE, new BorderIcon(combatant));
		this.addIcons(combatant);
	}
	
	private void addIcons(Combatant combatant) {
		this.setSlot(UtilityMenuSlot.ONE, new KitsIcon(combatant, new KitSelection(combatant)));
		this.setSlot(UtilityMenuSlot.TWO, new KitBuildersIcon(combatant, new KitBuilderSelection(combatant)));	
		this.setSlot(UtilityMenuSlot.THREE, new RolesIcon(combatant, new RoleSelection(combatant)));
		this.setSlot(UtilityMenuSlot.FOUR, new TeamsIcon(combatant, new InitialTeamOptions(combatant)));
		this.setSlot(UtilityMenuSlot.FIVE, new GuildsIcon(combatant, new InitialGuildOptions(combatant)));
		this.setSlot(UtilityMenuSlot.SIX, new HelpIcon(combatant, new HelpMenu(combatant)));
		this.setSlot(UtilityMenuSlot.NINE, new ExitIcon(combatant));		
	}
	
	@Override
	public Icon getIcon(MenuSlot menuSlot) {
		if(menuSlot instanceof UtilityMenuSlot) {
			return this.icons.get(menuSlot);
		}
		return null;
	}

	@Override
	public void setSlot(MenuSlot menuSlot, Icon icon) {
		if(menuSlot instanceof UtilityMenuSlot) {
			this.icons.put((UtilityMenuSlot) menuSlot, icon);
		}
	}

	@Override
	public void setMenu(Map<MenuSlot, Icon> icons) {
		Map<UtilityMenuSlot, Icon> tempIcons = new HashMap<>();
		for(MenuSlot menuSlot : icons.keySet()) {
			if(!(menuSlot instanceof UtilityMenuSlot)) {
				return;
			}
			tempIcons.put((UtilityMenuSlot) menuSlot, icons.get(menuSlot));
		}
		this.icons = tempIcons;
	}

	@Override
	public boolean isFull() {
		return this.icons.size() == 12;
	}

	@Override
	public int size() {
		return this.icons.size();
	}
	
	public Map<UtilityMenuSlot, Icon> getIcons() {
		return this.icons;
	}	
}
