package com.codari.apicore.itemdata;

import java.util.concurrent.atomic.AtomicInteger;

import com.codari.api5.itemdata.ItemDataManager;

public class ItemDataManagerCore implements ItemDataManager {
	//-----Fields-----//
	private final AtomicInteger idGenerator;
	
	//-----Constructor-----//
	public ItemDataManagerCore() {
		this.idGenerator = new AtomicInteger(1);
	}
}