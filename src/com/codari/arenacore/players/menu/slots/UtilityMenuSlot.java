package com.codari.arenacore.players.menu.slots;


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
	
	public static boolean isUtilityMenuSlot(int inventorySlot) {
		switch(inventorySlot) {
		case 15 :
		case 16 :
		case 17 :
		case 24 :
		case 25 :
		case 26 :
		case 33 :
		case 34 :
		case 35 :
		case 14 :
		case 23 :
		case 32 :
			return true;
		default :
			return false;
		}
	}	
	
	/**
	 * Method to get the menu slot associated with the actual bukkit slot.
	 * @param inventorySlot The actual slot from the bukkit inventory.
	 * @return The associated menu slot.
	 */
	public static UtilityMenuSlot getUtilitySlot(int inventorySlot) {
		switch(inventorySlot) {
		case 15 :
			return UtilityMenuSlot.ONE;
		case 16 : 
			return UtilityMenuSlot.TWO;
		case 17 : 
			return UtilityMenuSlot.THREE;
		case 24 :
			return UtilityMenuSlot.FOUR;
		case 25 :
			return UtilityMenuSlot.FIVE;
		case 26 :
			return UtilityMenuSlot.SIX;
		case 33 :
			return UtilityMenuSlot.SEVEN;
		case 34 :
			return UtilityMenuSlot.EIGHT;
		case 35 : 
			return UtilityMenuSlot.NINE;
		case 14 :
			return UtilityMenuSlot.SEP_ONE;
		case 23 :
			return UtilityMenuSlot.SEP_TWO;
		case 32 :
			return UtilityMenuSlot.SEP_THREE;
		default : 
			return UtilityMenuSlot.NO_SLOT;
		}
	}
}
