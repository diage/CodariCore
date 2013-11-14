package com.codari.apicore.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codari.api.stats.Stat;
import com.codari.api.stats.StatHolder;
import com.codari.api.stats.StatManager;

public final class StatManagerCore implements StatManager {
	//-----Fields-----//
	private final StatHolder holder;
	private final Map<String, Stat> stats;
	
	//-----Constructor-----//
	public StatManagerCore(StatHolder holder) {
		this.holder = holder;
		this.stats = new HashMap<>();
	}
	
	//-----Public Methods-----//
	@Override
	public StatHolder getHolder() {
		return this.holder;
	}
	
	@Override
	public Stat getStat(String name) {
		return this.stats.get(name);
	}
	
	@Override
	public List<Stat> getStats() {
		return new ArrayList<Stat>(this.stats.values());
	}
}