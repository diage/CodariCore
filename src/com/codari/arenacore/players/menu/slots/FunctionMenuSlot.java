package com.codari.arenacore.players.menu.slots;



public enum FunctionMenuSlot implements MenuSlot {
	A_ONE(9),
	A_TWO(10),
	A_THREE(11),
	A_FOUR(12),
	A_FIVE(13),
	B_ONE(18),
	B_TWO(19),
	B_THREE(20),
	B_FOUR(21),
	B_FIVE(22),
	C_ONE(27), 
	C_TWO(28), 
	C_THREE(29),
	C_FOUR(30),
	C_FIVE(31),
	NO_SLOT(-1);

	private int slot;
	
	private FunctionMenuSlot(int slot){
		this.slot = slot;
	}
	
	/**
	 * Gets the physical slot by bukkit standards that the menu slot sits in.
	 * @return The actual bukkit slot.
	 */
	public int getSlot() {
		return this.slot;
	}
	
	public static boolean isFunctionMenuSlot(int inventorySlot) {
		switch(inventorySlot) {
		case 9 :
		case 10 :
		case 11 :
		case 12 :
		case 13 :
		case 18 :
		case 19 :
		case 20 :
		case 21 :
		case 22 :
		case 27 :
		case 28 :
		case 29 :
		case 30 :
		case 31 :
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
	public static FunctionMenuSlot getFunctionSlot(int inventorySlot) {
		switch(inventorySlot) {
		case 9 :
			return FunctionMenuSlot.A_ONE;
		case 10 :
			return FunctionMenuSlot.A_TWO;
		case 11 :
			return FunctionMenuSlot.A_THREE;
		case 12 :
			return FunctionMenuSlot.A_FOUR;
		case 13 :
			return FunctionMenuSlot.A_FIVE;
		case 18 :
			return FunctionMenuSlot.B_ONE;
		case 19 :
			return FunctionMenuSlot.B_TWO;
		case 20 :
			return FunctionMenuSlot.B_THREE;
		case 21 :
			return FunctionMenuSlot.B_FOUR;
		case 22 :
			return FunctionMenuSlot.B_FIVE;
		case 27 :
			return FunctionMenuSlot.C_ONE;
		case 28 :
			return FunctionMenuSlot.C_TWO;
		case 29 :
			return FunctionMenuSlot.C_THREE;
		case 30 :
			return FunctionMenuSlot.C_FOUR;
		case 31 :
			return FunctionMenuSlot.C_FIVE;
		default: 
			return FunctionMenuSlot.NO_SLOT;
		}
	}
}
