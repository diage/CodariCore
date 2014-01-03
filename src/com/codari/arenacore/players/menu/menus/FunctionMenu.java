package com.codari.arenacore.players.menu.menus;

import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;
import com.codari.arenacore.players.menu.slots.MenuSlot;

public class FunctionMenu implements Menu {
	protected Map<FunctionMenuSlot, Icon> icons;
	
	public FunctionMenu(Combatant combatant) {
		this.icons = new HashMap<>();
	}
	
	@Override
	public Icon getIcon(MenuSlot menuSlot) {
		if(menuSlot instanceof FunctionMenuSlot) {
			return this.icons.get(menuSlot);
		}
		return null;
	}

	@Override
	public void setSlot(MenuSlot menuSlot, Icon icon) {
		if(menuSlot instanceof FunctionMenuSlot) {
			this.icons.put((FunctionMenuSlot) menuSlot, icon);
		}
	}

	@Override
	public void setMenu(Map<MenuSlot, Icon> icons) {
		Map<FunctionMenuSlot, Icon> tempIcons = new HashMap<>();
		for(MenuSlot menuSlot : icons.keySet()) {
			if(!(menuSlot instanceof FunctionMenuSlot)) {
				return;
			}
			tempIcons.put((FunctionMenuSlot) menuSlot, icons.get(menuSlot));
		}
		this.icons = tempIcons;
	}

	@Override
	public boolean isFull() {
		return this.icons.size() == 15;
	}

	@Override
	public int size() {
		return this.icons.size();
	}
	
	public void clearMenu() {
		this.icons.clear();
	}
	
	public Map<FunctionMenuSlot, Icon> getIcons() {
		return this.icons;
	}
	
	/** Gets the next available slot from the top two function menu rows.
	 * If there is no available slot, the method returns NO_SLOT */
	public FunctionMenuSlot getNextAvailableSlot() {
		int counter = 0;
		for(FunctionMenuSlot functionMenuSlot : this.icons.keySet()) {
			if(functionMenuSlot.getSlot() >= 9 && functionMenuSlot.getSlot() <= 22) {
				counter++;
			}
		}

		switch(counter) {
		case 0:
			return FunctionMenuSlot.A_ONE;
		case 1:
			return FunctionMenuSlot.A_TWO;
		case 2:
			return FunctionMenuSlot.A_THREE;
		case 3:
			return FunctionMenuSlot.A_FOUR;
		case 4:
			return FunctionMenuSlot.A_FIVE;
		case 5:
			return FunctionMenuSlot.B_ONE;
		case 6:
			return FunctionMenuSlot.B_TWO;
		case 7:
			return FunctionMenuSlot.B_THREE;
		case 8:
			return FunctionMenuSlot.B_FOUR;
		case 9:
			return FunctionMenuSlot.B_FIVE;
		default:
			return FunctionMenuSlot.NO_SLOT;
		}
	}
}
