package com.codari.apicore.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.codari.api5.stats.Stat;
import com.codari.api5.stats.StatHolder;
import com.codari.api5.stats.StatManager;
import com.codari.api5.stats.StatType;

public final class StatManagerCore implements StatManager {
	//-----Fields-----//
	private final StatHolder holder;
	private final Map<String, Stat> stats;
	
	//-----Constructor-----//
	public StatManagerCore(StatHolder holder, Set<StatType> statTypes) {
		this.holder = holder;
		this.stats = new HashMap<>();
		for (StatType statType : statTypes) {
			stats.put(statType.getName().toLowerCase(), new StatCore(statType, this));
		}
	}
	
	//-----Public Methods-----//
	@Override
	public StatHolder getHolder() {
		return this.holder;
	}
	
	@Override
	public Stat getStat(String name) {
		return this.stats.get(name.toLowerCase());
	}
	
	@Override
	public List<Stat> getStats() {
		return new ArrayList<Stat>(this.stats.values());
	}
	
	//-----Utility Methods-----//
	@Override
	public Iterator<Stat> iterator() {
		return new StatIterator();
	}
	
	//-----Stat Iterator-----//
	private final class StatIterator implements Iterator<Stat> {
		//-----Fields-----//
		private final Iterator<Stat> statIterator;
		
		//-----Constructor-----//
		private StatIterator() {
			this.statIterator = stats.values().iterator();
		}
		//-----Methods-----//
		@Override
		public boolean hasNext() {
			return this.statIterator.hasNext();
		}
		
		@Override
		public Stat next() {
			return this.statIterator.next();
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}