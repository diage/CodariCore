package com.codari.arenacore.players.menu.events;

import com.codari.arenacore.players.menu.icons.structure.Icon;


public class IconHoverUpdateEvent extends IconInteractEvent {
	private int newInput; 
	private int oldInput;
	
	public IconHoverUpdateEvent(Icon icon, int newInput, int oldInput) {
		super(icon);
		this.newInput = newInput;
		this.oldInput = oldInput;
	}
	
	public int getNewInput() {
		return this.newInput;
	}
	
	public int getOldInput() {
		return this.oldInput;
	}

}
