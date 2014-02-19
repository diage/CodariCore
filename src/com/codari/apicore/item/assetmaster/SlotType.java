package com.codari.apicore.item.assetmaster;

import java.util.NoSuchElementException;

public enum SlotType {

	ATTRIBUTE(0, 1, 2),
	USE(3),
	INCREMENTAL(4),
	STACK(5),
	PREFIX(6),
	SUFFIX(7);

	public final static int MAX_NUMBER_OF_ATTRIBUTES_LENGTH = SlotType.ATTRIBUTE.getSlotValues().length;
	public final static int MAX_NUMBER_OF_USE_ASSETS = SlotType.USE.getSlotValues().length;
	public final static int MAX_NUMBER_OF_INCREMENTAL_ASSETS = SlotType.INCREMENTAL.getSlotValues().length;
	public final static int MAX_NUMBER_OF_STACK_ASSETS = SlotType.STACK.getSlotValues().length;
	public final static int MAX_NUMBER_OF_PREFIX_ASSETS = SlotType.PREFIX.getSlotValues().length;
	public final static int MAX_NUMBER_OF_SUFFIX_ASSETS = SlotType.SUFFIX.getSlotValues().length;
	
	private int[] values;
	
	private SlotType(int ...values) {
		this.values = values;
	}
	
	public int[] getSlotValues() {
		return this.values;
	}
	
	public static SlotType getSlotType(int value) {
		switch(value) {
		case 0:
		case 1:
		case 2:
			return ATTRIBUTE;
		case 3:
			return USE;
		case 4:
			return INCREMENTAL;
		case 5: 
			return STACK;
		case 6:
			return PREFIX;
		case 7:
			return SUFFIX;
		default:
			throw new NoSuchElementException();
		}
	}
}
