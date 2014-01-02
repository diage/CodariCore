package com.codari.arenacore.arena;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

import com.codari.api5.CodariI;
import com.codari.api5.io.ConfigurationOutput;
import com.codari.api5.io.ConfigurationOutput.OutputFunction;
import com.codari.api5.util.SerializableLocation;
import com.codari.api5.util.Time;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.DelayedPersistentObject;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;

public class ArenaBuilderCore implements ArenaBuilder {
	private static final long serialVersionUID = 4720468390178286776L;
	//-----Fields-----//
	private GameRule rules;
	private final Map<String, RandomTimelineGroup> randomSpawnables;
	private final List<FixedSpawnableAction> fixedSpawnables;
	private final List<ImmediatePersistentObject> immediatePersistentObjects;
	private final List<DelayedPersistentObject> delayedPersistentObjects;
	private final List<ArenaObject> objects;
	private final List<SerializableLocation> spawners;
	
	//-----Constructor-----//
	public ArenaBuilderCore(GameRule rules) {
		this.rules = rules;
		this.randomSpawnables = new HashMap<>();
		this.fixedSpawnables = new ArrayList<>();
		this.immediatePersistentObjects = new ArrayList<>();
		this.delayedPersistentObjects = new ArrayList<>();
		this.objects = new ArrayList<>();
		this.spawners = new ArrayList<>();
	}
	
	//-----Public Methods-----//
	public List<TimedAction> compileActions() {
		List<TimedAction> actions = new ArrayList<>();
		actions.addAll(this.rules.getTimedActions());
		actions.addAll(this.randomSpawnables.values());
		actions.addAll(this.fixedSpawnables);
		return actions;
	}
	
	public List<ArenaObject> compileObjects() {
		return this.objects;
	}
	
	public List<SerializableLocation> compileSpawners() {
		return this.spawners;
	}
	
	public void setGameRule(GameRule rule) {
		this.rules = rule;
	}
	
	@Override
	public GameRule getGameRule() {
		return this.rules;
	}
	
	@Override
	public boolean createRandomTimelineGroup(String groupName, Time time) {
		return this.createRandomTimelineGroup(groupName, time, Time.NULL);
	}
	
	@Override
	public boolean createRandomTimelineGroup(String groupName, Time time, Time repeatTime) {
		//FIXME - check time
		if (this.randomSpawnables.containsKey(groupName)) {
			return false;
		}
		this.randomSpawnables.put(groupName, new RandomTimelineGroup(time, repeatTime));
		return true;
	}
	
	@Override
	public boolean registerRandomSpawnable(RandomSpawnableObject object, String groupName) {
		RandomTimelineGroup randomTimelineGroup = this.randomSpawnables.get(groupName);
		if (randomTimelineGroup == null) {
			return false;
		}
		randomTimelineGroup.addObject(object);
		this.objects.add(object);
		return true;
	}
	
	@Override
	public boolean checkForRandomSpawnableGroup(String name) {
		if(this.randomSpawnables.containsKey(name)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean registerFixedSpawnable(FixedSpawnableObject object, Time time) {
		this.objects.add(object);
		return this.registerFixedSpawnable(object, time, Time.NULL);
	}
	
	@Override
	public boolean registerFixedSpawnable(FixedSpawnableObject object, Time time, Time repeatTime) {
		//FIXME - check time
		this.fixedSpawnables.add(new FixedSpawnableAction(object, time, repeatTime));
		this.objects.add(object);
		return true;
	}
	
	@Override
	public boolean registerPersistent(ImmediatePersistentObject immediatePersistentObject) {
		this.immediatePersistentObjects.add(immediatePersistentObject);
		this.objects.add(immediatePersistentObject);
		return true;
	}
	
	@Override
	public boolean registerPersistent(DelayedPersistentObject delayedPersistentObject, Time time, boolean override) {
		this.delayedPersistentObjects.add(delayedPersistentObject);
		this.objects.add(delayedPersistentObject);
		return true;
	}
	
	public ArrayList<ArenaObject> getArenaObjectsCopyList() {
		return new ArrayList<ArenaObject>(this.objects);
	}
	
	public Map<String, RandomTimelineGroup> getRandomSpawnablesCopyMap() {
		return new HashMap<String, RandomTimelineGroup>(this.randomSpawnables);
	}	
	
	//-----Random Timeline Group-----//
	private final static class RandomTimelineGroup extends TimedAction {
		private static final long serialVersionUID = -1455939781657809306L;

		private final static Random globalRandom = new Random(System.currentTimeMillis());
		
		//-----Fields-----//
		private final List<Marble> bagOfMarbles;
		private final Random random;
		
		//-----Constructor-----//
		public RandomTimelineGroup(Time delay, Time period) {
			super(null, delay, period);
			this.bagOfMarbles = new ArrayList<>();
			this.random = new Random(System.currentTimeMillis() + globalRandom.nextInt());
		}

		@Override
		public void action() {
			if (!this.bagOfMarbles.isEmpty()) {
				int i = this.random.nextInt(this.bagOfMarbles.size());
				Marble marble = this.bagOfMarbles.get(i);
				if (!marble.object.isSpawned()) {
					marble.object.spawn();
				}
			}
		}
		
		private void addObject(RandomSpawnableObject object) {
			for (int i = 0; i < object.getWeight(); i++) {
				this.bagOfMarbles.add(new Marble(object));
			}
		}
		
		//THINK OF MARBLES IN A BAG
		private final class Marble implements Serializable {
			private static final long serialVersionUID = 2120469391762175063L;
			private final RandomSpawnableObject object;
			public Marble(RandomSpawnableObject object) {
				this.object = object;
			}
		}
	}
	
	private final static class FixedSpawnableAction extends TimedAction {
		private static final long serialVersionUID = 3857488726629177537L;
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
	
	@SuppressWarnings("unused")
	private final static class DelayedPersistentAction implements DelayedPersistentObject {
		private static final long serialVersionUID = 2824909986948144853L;
		private DelayedPersistentObject delayedPersistentObject;
		private final Time time;
		private final boolean override;
		private BukkitTask task;
		
		public DelayedPersistentAction(DelayedPersistentObject delayedPersistentObject, Time time, boolean override) {
			this.delayedPersistentObject = delayedPersistentObject;
			this.time = time;
			this.override = override;
		}
		
		@Override
		public void interact() {
			if (this.override && this.task != null) {
				this.task.cancel();
			}
			this.task = Bukkit.getScheduler().runTaskLater(CodariI.INSTANCE, new Runnable() {
				@Override
				public void run() {
					delayedPersistentObject.interact();
					task = null;
				}
			}, time.ticks());
		}

		@Override
		public void reveal() {
			this.delayedPersistentObject.reveal();
		}

		@Override
		public void hide() {
			this.delayedPersistentObject.hide();
		}
	}

	@Override
	public void addSpawnLocation(Location location) {
		this.spawners.add(new SerializableLocation(location));
	}

	@Override
	public Map<String, Object> serialize() {
		return new ConfigurationOutput()
				.result();
	}
	
	//-----Object List Output Function-----//
	private final static class ObjectListOutputFunction implements OutputFunction<List<ArenaObject>> {
		@Override
		public Map<String, Object> apply(@Nullable List<ArenaObject> objectList) {
			Map<String, Object> result = new LinkedHashMap<>();
			for (ArenaObject object : objectList) {
				
			}
			return result;
		}
	}
}
