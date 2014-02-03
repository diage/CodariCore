package com.codari.apicore.itemdata;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.codari.api5.itemdata.ItemDataManager;

public class ItemDataManagerCore implements ItemDataManager {
	//-----Fields-----//
	private final UUID sessionId = UUID.randomUUID();
	private final AtomicInteger idGenerator;
	private final Map<Integer, ItemDataCore> data;
	
	//-----Constructor-----//
	public ItemDataManagerCore() {
		this.idGenerator = new AtomicInteger(1);
		this.data = new HashMap<>();
	}
}
