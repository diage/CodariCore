package com.codari.apicore.item;

import java.util.HashMap;
import java.util.Map;

import com.codari.apicore.util.Codec;
import com.codari.arena5.item.CodariItemManager;

public class CodariItemManagerCore implements CodariItemManager {
	//-----Fields-----//
	private final Map<Integer, CodariItemCore> data;
	private String sessionId;
	private int idIncrement;
	
	//-----Constructor-----//
	public CodariItemManagerCore() {
		this.data = new HashMap<>();
		this.newSession();
	}
	
	//-----Methods-----//
	private void newSession() {
		this.sessionId = Codec.BASE62.encode(System.currentTimeMillis());
		this.idIncrement = 0;
		this.data.clear();
	}
}
