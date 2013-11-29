package com.codari.arenacore.players.menu;
/**
 * Enumeration representing the menu slots located within the menu.
 * @author Diage, Soren025
 *
 */
public enum UtilityMenuSlot implements MenuSlot{
	ONE(15),
	TWO(16),
	THREE(17),
	FOUR(24),
	FIVE(25),
	SIX(26),
	SEVEN(33),
	EIGHT(34),
	NINE(35),
	SEP_ONE(14),
	SEP_TWO(23),
	SEP_THREE(32),
	NO_SLOT(-1);

	private int slot;
	
	private UtilityMenuSlot(int slot){
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
	public static UtilityMenuSlot getUtilitySlot(int inventorySlot) {
		switch(inventorySlot) {
		case 12 :
			return UtilityMenuSlot.ONE;
		case 13 : 
			return UtilityMenuSlot.TWO;
		case 14 : 
			return UtilityMenuSlot.THREE;
		case 21 :
			return UtilityMenuSlot.FOUR;
		case 22 :
			return UtilityMenuSlot.FIVE;
		case 23 :
			return UtilityMenuSlot.SIX;
		case 30 :
			return UtilityMenuSlot.SEVEN;
		case 31 :
			return UtilityMenuSlot.EIGHT;
		case 32 : 
			return UtilityMenuSlot.NINE;
		default : 
			return UtilityMenuSlot.NO_SLOT;
		}
	}
}
