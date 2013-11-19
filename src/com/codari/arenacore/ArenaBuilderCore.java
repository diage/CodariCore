package com.codari.arenacore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.codari.api5.util.Time;
import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.objects.persistant.DelayedPersistentObject;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.rules.GameRule;
import com.codari.arena5.rules.TimedAction;

public class ArenaBuilderCore implements ArenaBuilder {
	//-----Fields-----//
	private final GameRule rules;
	private final Map<String, RandomTimelineGroup> randomSpawnables;
	private final List<FixedSpawnableAction> fixedSpawnables;
	
	//-----Constructor-----//
	public ArenaBuilderCore(GameRule rules) {
		this.rules = rules;
		this.randomSpawnables = new HashMap<>();
		this.fixedSpawnables = new ArrayList<>();
	}
	
	//-----Public Methods-----//
	public List<TimedAction> compileActions() {
		List<TimedAction> actions = new ArrayList<>();
		actions.addAll(this.randomSpawnables.values());
		actions.addAll(this.fixedSpawnables);
		return actions;
	}
	
	@Override
	public boolean createRandomTimelineGroup(String groupName, Time time) {
		return this.createRandomTimelineGroup(groupName, time, Time.NULL);
	}
	
	@Override
	public boolean createRandomTimelineGroup(String groupName, Time time, Time repeatTime) {
		//TODO check time
		if (this.randomSpawnables.containsKey(groupName)) {
			return false;
		}
		this.randomSpawnables.put(groupName, new RandomTimelineGroup(time, repeatTime));
		return true;
	}
	
	@Override
	public boolean registerRandomSpawnable(RandomSpawnableObject object, String groupName) {
		RandomTimelineGroup g = this.randomSpawnables.get(groupName);
		if (g == null) {
			return false;
		}
		g.spawns.add(object);
		return true;
	}
	
	@Override
	public boolean registerFixedSpawnable(FixedSpawnableObject object, Time time) {
		return this.registerFixedSpawnable(object, time, Time.NULL);
	}
	
	@Override
	public boolean registerFixedSpawnable(FixedSpawnableObject object, Time time, Time repeatTime) {
		//TODO check time
		this.fixedSpawnables.add(new FixedSpawnableAction(object, time, repeatTime));
		return true;
	}
	
	@Override
	public boolean registerPersistent(ImmediatePersistentObject object) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean registerPersistent(DelayedPersistentObject object, Time time, boolean override) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//-----Random Timeline Group-----//
	private final static class RandomTimelineGroup extends TimedAction {
		private final static Random globalRandom = new Random(System.currentTimeMillis());
		
		//-----Fields-----//
		private final List<RandomSpawnableObject> spawns;
		private final Random random;
		
		//-----Constructor-----//
		public RandomTimelineGroup(Time delay, Time period) {
			super(null, delay, period);
			this.spawns = new ArrayList<>();
			this.random = new Random(System.currentTimeMillis() + globalRandom.nextInt());
		}

		@Override
		public void action() {
			if (!this.spawns.isEmpty()) {
				int i = this.random.nextInt(this.spawns.size());
				RandomSpawnableObject o = this.spawns.get(i);
				o.spawn();
			}
		}
	}
	
	private final static class FixedSpawnableAction extends TimedAction {
		//-----Fields-----//
		private final FixedSpawnableObject spawnable;
		
		public FixedSpawnableAction(FixedSpawnableObject spawnable, Time delay, Time period) {
			super(null, delay, period);
			this.spawnable = spawnable;
		}
		
		@Override
		public void action() {
			this.spawnable.spawn();
		}
	}
}