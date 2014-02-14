package com.codari.apicore.item;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.codari.apicore.CodariCore;
import com.codari.arena5.item.CodariItemManager;

public class CodariItemManagerCore implements CodariItemManager {
	//-----Fields-----//
	private final File itemData;
	private final UUID sessionId = UUID.randomUUID();
	private final AtomicInteger idGenerator = new AtomicInteger(1);
	private final Map<Integer, CodariItemCore> data;
	
	//-----Constructor-----//
	public CodariItemManagerCore() {
		this.itemData = new File(CodariCore.instance().getDataFolder(), "items.dat");
		this.data = new HashMap<>();
	}
}
