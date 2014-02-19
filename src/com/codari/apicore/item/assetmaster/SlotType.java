package com.codari.apicore.item.assetmaster;

import java.util.NoSuchElementException;

import org.apache.commons.lang.ArrayUtils;

public enum SlotType {

	ATTRIBUTE(0, 1, 2),
	USE(3),
	INCREMENTAL(4),
	STACK(5),
	PREFIX(6),
	SUFFIX(7);
	
	public static final int TOTAL_SLOTS;
	static {
		int total = 0;
		for (SlotType value : values()) {
			total += value.max();
		}
		TOTAL_SLOTS = total;
	}
	
	private int[] values;
	
	private SlotType(int ...values) {
		this.values = values;
	}
	
	public int[] getSlotValues() {
		return ArrayUtils.clone(this.values);
	}
	
	public int max() {
		return this.values.length;
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
