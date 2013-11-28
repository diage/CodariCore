package com.codari.arenacore.players.menu;
/**
 * Enumeration representing the menu slots located within the menu.
 * @author Diage, Soren025
 *
 */
public enum MenuSlot {
	ONE(12),
	TWO(13),
	THREE(14),
	FOUR(21),
	FIVE(22),
	SIX(23),
	SEVEN(30),
	EIGHT(31),
	NINE(32),
	NO_SLOT(-1);

	private int slot;
	
	private MenuSlot(int slot){
		this.slot = slot;
	}
	
	/**
	 * Gets the physical slot by bukkit standards that the menu slot sits in.
	 * @return The actual bukkit slot.
	 */
	public int getSlot() {
		return this.slot;
	}
	
	/**
	 * Method to get the menu slot associated with the actual bukkit slot.
	 * @param inventorySlot The actual slot from the bukkit inventory.
	 * @return The associated menu slot.
	 */
	public static MenuSlot getMenuSlot(int inventorySlot) {
		switch(inventorySlot) {
		case 12 :
			return MenuSlot.ONE;
		case 13 : 
			return MenuSlot.TWO;
		case 14 : 
			return MenuSlot.THREE;
		case 21 :
			return MenuSlot.FOUR;
		case 22 :
			return MenuSlot.FIVE;
		case 23 :
			return MenuSlot.SIX;
		case 30 :
			return MenuSlot.SEVEN;
		case 31 :
			return MenuSlot.EIGHT;
		case 32 : 
			return MenuSlot.NINE;
		default : 
			return MenuSlot.NO_SLOT;
		}
	}
}
