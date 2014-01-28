package com.codari.arenacore.arena;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.io.ConfigurationInput;
import com.codari.api5.io.ConfigurationInput.InputFunction;
import com.codari.api5.io.ConfigurationOutput;
import com.codari.api5.io.ConfigurationOutput.OutputFunction;
import com.codari.api5.util.SerializableLocation;
import com.codari.api5.util.Time;
import com.codari.apicore.CodariCore;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.DelayedPersistentObject;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.arena.rules.GameRuleCore;

@SerializableAs("Arena_Builder")
public class ArenaBuilderCore implements ArenaBuilder {
	//-----Fields-----//
	private String name;
	private GameRule rules;
	private final Map<String, RandomTimelineGroup> randomSpawnables;
	private final List<FixedSpawnableAction> fixedSpawnables;
	private final List<ImmediatePersistentObject> immediatePersistentObjects;
	private final List<DelayedPersistentObject> delayedPersistentObjects;
	private final List<ArenaObject> objects;
	private final List<String> objectsWithLinks;	//FIXME - when arena objects are loaded, if they do have links they have to be added here
	private final List<SerializableLocation> spawners;
	private final List<ObjectDataPacket> data;
	
	//-----Constructor-----//
	public ArenaBuilderCore(String name, GameRuleCore rules) {
		this.name = name;
		this.rules = rules;
		this.randomSpawnables = new HashMap<>();
		this.fixedSpawnables = new ArrayList<>();
		this.immediatePersistentObjects = new ArrayList<>();
		this.delayedPersistentObjects = new ArrayList<>();
		this.objects = new ArrayList<>();
		this.objectsWithLinks = new ArrayList<>();
		this.spawners = new ArrayList<>();
		this.data = new ArrayList<>();
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
	
	public int getNumberOfSpawns() {
		return this.spawners.size();
	}
	
	public void setGameRule(GameRule rule) {
		this.rules = rule;
	}
	
	public String getName() {
		return this.name;
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
		if (this.randomSpawnables.containsKey(groupName)) {
			return false;
		}
		this.randomSpawnables.put(groupName, new RandomTimelineGroup(groupName, time, repeatTime));
		return true;
	}
	
	@Override
	public boolean registerRandomSpawnable(RandomSpawnableObject object, String groupName) {
		RandomTimelineGroup randomTimelineGroup = this.randomSpawnables.get(groupName);
		if (randomTimelineGroup == null) {
			return false;
		}
		randomTimelineGroup.addObject(object);
		this.addArenaObject(object);
		this.data.add(new ObjectDataPacket(object, groupName));
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
		this.addArenaObject(object);
		return this.registerFixedSpawnable(object, time, Time.NULL);
	}
	
	@Override
	public boolean registerFixedSpawnable(FixedSpawnableObject object, Time time, Time repeatTime) {
		//FIXME - check time
		this.fixedSpawnables.add(new FixedSpawnableAction(object, time, repeatTime));
		this.addArenaObject(object);
		this.data.add(new ObjectDataPacket(object, time.toString(), repeatTime.toString()));
		return true;
	}
	
	@Override
	public boolean registerPersistent(ImmediatePersistentObject immediatePersistentObject) {
		this.immediatePersistentObjects.add(immediatePersistentObject);
		this.addArenaObject(immediatePersistentObject);
		this.data.add(new ObjectDataPacket(immediatePersistentObject));
		return true;
	}
	
	@Override
	public boolean registerPersistent(DelayedPersistentObject delayedPersistentObject, Time time, boolean override) {
		this.delayedPersistentObjects.add(delayedPersistentObject);
		this.addArenaObject(delayedPersistentObject);
		this.data.add(new ObjectDataPacket(delayedPersistentObject, time.toString(), Boolean.toString(override)));
		return true;
	}
	
	public ArrayList<ArenaObject> getArenaObjectsCopyList() {
		return new ArrayList<ArenaObject>(this.objects);
	}
	
	public Map<String, RandomTimelineGroup> getRandomSpawnablesCopyMap() {
		return new HashMap<String, RandomTimelineGroup>(this.randomSpawnables);
	}
	
	public boolean hasAllLinks(Collection<String> links) {
		for(String link : this.objectsWithLinks) {
			if(!links.contains(link)) {
				return false;
			}
		}
		return true;
	}
	
	private void addArenaObject(ArenaObject object) {
		if(((LibraryCore) Codari.getLibrary()).getLinks(object.getName()) != null) {
			this.objectsWithLinks.add(object.getName());
		}
		this.objects.add(object);
	}
	
	//-----Random Timeline Group-----//
	private final static class RandomTimelineGroup extends TimedAction {
		private static final long serialVersionUID = -1455939781657809306L;

		private final static Random globalRandom = new Random(System.currentTimeMillis());
		
		//-----Fields-----//
		private final List<Marble> bagOfMarbles;
		private final Random random;
		private final String name;
		
		//-----Constructor-----//
		public RandomTimelineGroup(String name, Time delay, Time period) {
			super(null, delay, period);
			this.name = name;
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

	@Override
	public void addSpawnLocation(Location location) {
		this.spawners.add(new SerializableLocation(location));
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new ConfigurationOutput()
		.addString("GameRule", this.getGameRule().getName())
		.add(new TimelineGroupOutputFunction(), new ArrayList<RandomTimelineGroup>(this.randomSpawnables.values()))
		.add(new DataOutputFunction(), this.data).result();
		result.put("GameRule", this.getGameRule().getName());
		result.put("name", this.name);
		ArenaManagerCore arenaManager = (ArenaManagerCore) Codari.getArenaManager();
		int i = 0;
		for (String s : arenaManager.getExistingRoleNames(this.name)) {
			result.put("role_" + i, s);
			i++;
		}
		return result;
	}
	
	public static ArenaBuilderCore deserialize(Map<String, Object> args) {
		ConfigurationInput input = new ConfigurationInput(args);
		ArenaManagerCore arenaManager = (ArenaManagerCore) Codari.getArenaManager();
		GameRule gameRule = arenaManager.getGameRule(input.getString("GameRule"));
		String name = input.getString("name");
		ArenaBuilderCore builder = (ArenaBuilderCore) arenaManager.getArenaBuider(name, gameRule);
		List<RandomTimelineGroup> randomTimelineGroups = input.get(new TimelineGroupInputFunction());
		for (RandomTimelineGroup g : randomTimelineGroups) {
			builder.randomSpawnables.put(g.name, g);
		}
		List<ObjectDataPacket> dataList = input.get(new DataInputFunction());
		for (ObjectDataPacket data : dataList) {
			data.apply(builder);
		}
		for (int i = 0; args.containsKey("role_" + i); i++) {
			String rName = input.getString("role_" + i);
			arenaManager.submitRole(name, ((CodariCore) CodariI.INSTANCE).getRoleManager().getRole(rName));
		}
		//TODO
		Bukkit.broadcastMessage("POTATO DEBUG IS THIS NULL? " + String.valueOf(builder));
		return builder;
	}
	
	private final static class TimelineGroupOutputFunction implements OutputFunction<List<RandomTimelineGroup>> {
		@Override
		public Map<String, Object> apply(@Nullable List<RandomTimelineGroup> timelineGroups) {
			Map<String, Object> result = new LinkedHashMap<>();
			for (int i = 0; i < timelineGroups.size(); i++) {
				result.put("Group_Name_" + i, timelineGroups.get(i).name);
				result.put("Group_Delay_" + i, timelineGroups.get(i).getDelay().toString());
				result.put("Group_Period_" + i, timelineGroups.get(i).getPeriod().toString());
			}
			return result;
		}
	}
	
	private final static class TimelineGroupInputFunction implements InputFunction<List<RandomTimelineGroup>> {
		@Override
		public List<RandomTimelineGroup> apply(@Nullable Map<String, Object> args) {
			List<RandomTimelineGroup> result = new ArrayList<>();
			for (int i = 0;; i++) {
				Object obj = args.get("Group_Name_" + i);
				if (obj != null) {
					String name = (String) obj;
					long delay = Long.valueOf((String) args.get("Group_Delay_" + i));
					long period = Long.valueOf((String) args.get("Group_Period_" + i));
					result.add(new RandomTimelineGroup(name, new Time(0, 0, delay), new Time(0, 0, period)));
				} else {
					break;
				}
			}
			return result;
		}
	}
	
	//-----Object Data Packet-----//
	@SerializableAs("Oject_Data_Packet")
	public final static class ObjectDataPacket implements ConfigurationSerializable {
		private final String objectName;
		private final SerializableLocation location;
		private final List<String> extraInformation;
		
		//-----Constructor-----//
		private ObjectDataPacket(ArenaObject obj, String... extraInformation) {
			this.objectName = obj.getName();
			this.location = new SerializableLocation(obj.getLocation());
			this.extraInformation = Arrays.asList(extraInformation);
		}
		
		private void apply(ArenaBuilderCore builder) {
			ArenaObject arenaObject = ((LibraryCore) Codari.getLibrary()).createObject(this.objectName, this.location.getLocation());
			if(arenaObject instanceof RandomSpawnableObject) {
				builder.registerRandomSpawnable((RandomSpawnableObject) arenaObject, extraInformation.get(0));
			} else if(arenaObject instanceof FixedSpawnableObject) {
				if (extraInformation != null) {
					if(extraInformation.size() == 1) {
						builder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, new Time(0, 0, Long.parseLong(extraInformation.get(0))));
					} else if(extraInformation.size() >= 2) {
						builder.registerFixedSpawnable((FixedSpawnableObject) arenaObject, new Time(0, 0, Long.parseLong(extraInformation.get(0))), new Time(0, 0, Long.parseLong(extraInformation.get(1))));
					}
				}
			} else if(arenaObject instanceof ImmediatePersistentObject) {
				builder.registerPersistent((ImmediatePersistentObject) arenaObject);
			} else if(arenaObject instanceof DelayedPersistentObject) {
				if(extraInformation != null && extraInformation.size() >= 2) {
					builder.registerPersistent((DelayedPersistentObject) arenaObject, new Time(0, 0, Long.parseLong(extraInformation.get(0))), Boolean.parseBoolean(extraInformation.get(1)));
				}
			}
		}

		@Override
		public Map<String, Object> serialize() {
			return new ConfigurationOutput()
					.addString("name", this.objectName)
					.addObject("location", this.location)
					.add(new ExtraInformationOutputFunction(), this.extraInformation)
					.result();
		}
		
		public ObjectDataPacket(Map<String, Object> args) {
			ConfigurationInput input = new ConfigurationInput(args);
			this.objectName = input.getString("name");
			this.location = input.getObject(SerializableLocation.class, "location");
			this.extraInformation = input.get(new ExtraInformationInputFunction());
		}
	}
	
	//-----Extra Information Configuration Functions-----//
	private final static class ExtraInformationOutputFunction implements OutputFunction<List<String>> {
		@Override
		public Map<String, Object> apply(@Nullable List<String> ext) {
			Map<String, Object> result = new LinkedHashMap<>();
			for (int i = 0; i < ext.size(); i++) {
				result.put("Info_" + i, ext.get(i));
			}
			return result;
		}
	}
	
	private final static class ExtraInformationInputFunction implements InputFunction<List<String>> {
		@Override
		public List<String> apply(@Nullable Map<String, Object> args) {
			List<String> result = new ArrayList<>();
			for (int i = 0;; i++) {
				Object obj = args.get("Info_" + i);
				if (obj != null) {
					result.add((String) obj);
				} else {
					break;
				}
			}
			return result;
		}
	}
	
	private final static class DataOutputFunction implements OutputFunction<List<ObjectDataPacket>> {
		@Override
		public Map<String, Object> apply(@Nullable List<ObjectDataPacket> data) {
			Map<String, Object> result = new LinkedHashMap<>();
			for (int i = 0; i < data.size(); i++) {
				result.put("Data_" + i, data.get(i));
			}
			return result;
		}
	}
	
	private final static class DataInputFunction implements InputFunction<List<ObjectDataPacket>> {
		@Override
		public List<ObjectDataPacket> apply(@Nullable Map<String, Object> args) {
			List<ObjectDataPacket> result = new ArrayList<>();
			for (int i = 0;; i++) {
				Object obj = args.get("Data_" + i);
				if (obj != null) {
					result.add((ObjectDataPacket) obj);
				} else {
					break;
				}
			}
			return result;
		}
	}
}
