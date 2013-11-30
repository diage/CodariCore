package com.codari.arenacore.players.menu.events;

import com.codari.arenacore.players.menu.icons.SelectionIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;

public class IconSelectionEvent extends IconInteractEvent {
	private boolean selected;
	
	public IconSelectionEvent(Icon icon) {
		super(icon);
		if(icon instanceof SelectionIcon) {
			if(((SelectionIcon) icon).isSelected()) {
				this.selected = true;
			} else {
				this.selected = false;
			}
		}
	}
	
	public boolean isSelected() {
		return this.selected;
	}

}
