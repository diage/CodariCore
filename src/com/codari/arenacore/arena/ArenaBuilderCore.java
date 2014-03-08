package com.codari.arenacore.arena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import net.minecraft.util.org.apache.commons.lang3.RandomStringUtils;

import org.bukkit.block.BlockState;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.metadata.FixedMetadataValue;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.io.ConfigurationInput;
import com.codari.api5.io.ConfigurationInput.InputFunction;
import com.codari.api5.io.ConfigurationOutput;
import com.codari.api5.io.ConfigurationOutput.OutputFunction;
import com.codari.api5.util.SerializableLocation;
import com.codari.api5.util.Time;
import com.codari.apicore.CodariCore;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.DelayedPersistentObject;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.role.Role;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.arena.objects.RoleData;
import com.codari.arenacore.arena.objects.RoleSelectionObject;
import com.codari.arenacore.arena.objects.SpawnPoint;
import com.codari.arenacore.arena.rules.GameRule;
import com.codari.arenacore.arena.rules.GameRuleCore;
import com.codari.arenacore.players.builders.kit.ToolBarListener;

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
	private final List<String> linkedObjects;	//FIXME - when arena objects are loaded, if they do have links they have to be added here
	private final List<SerializableLocation> spawners;
	private final List<ObjectDataPacket> data;

	//-----Shadow Lists-----//
	private final List<String> objectShadows;
	private final List<String> randomSpawnableShadows;
	private final List<String> randomSpawnableGroupShadows;
	private final List<String> fixedSpawnableShadows;
	private final List<String> immediatePersistentShadows;
	private final List<String> delayedPersistentShadows;
	private final List<String> linkedObjectsShadows;
	private final List<String> spawnerShadows;

	//-----Constructor-----//
	public ArenaBuilderCore(String name, GameRuleCore rules) {
		this.name = name;
		this.rules = rules;
		this.randomSpawnables = new HashMap<>();
		this.fixedSpawnables = new ArrayList<>();
		this.immediatePersistentObjects = new ArrayList<>();
		this.delayedPersistentObjects = new ArrayList<>();
		this.objects = new ArrayList<>();
		this.linkedObjects = new ArrayList<>();
		this.spawners = new ArrayList<>();
		this.data = new ArrayList<>();

		//-----Shadows-----//
		this.objectShadows = new ArrayList<>();
		this.randomSpawnableShadows = new ArrayList<>();
		this.randomSpawnableGroupShadows = new ArrayList<>();
		this.fixedSpawnableShadows = new ArrayList<>();
		this.immediatePersistentShadows = new ArrayList<>();
		this.delayedPersistentShadows = new ArrayList<>();
		this.linkedObjectsShadows = new ArrayList<>();
		this.spawnerShadows = new ArrayList<>();
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
		String metaDataValue = this.generateRandomMetaData();
		this.randomSpawnableShadows.add(metaDataValue);
		this.randomSpawnableGroupShadows.add(groupName);
		this.addArenaObject(object, metaDataValue);
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
		return this.registerFixedSpawnable(object, time, Time.NULL);
	}

	@Override
	public boolean registerFixedSpawnable(FixedSpawnableObject object, Time time, Time repeatTime) {
		//FIXME - check time
		String metaDataValue = this.generateRandomMetaData();
		this.fixedSpawnables.add(new FixedSpawnableAction(object, time, repeatTime));
		this.fixedSpawnableShadows.add(metaDataValue);
		this.addArenaObject(object, metaDataValue);
		this.data.add(new ObjectDataPacket(object, time.toString(), repeatTime.toString()));
		return true;
	}

	@Override
	public boolean registerPersistent(ImmediatePersistentObject immediatePersistentObject) {
		String metaDataValue = this.generateRandomMetaData();
		this.immediatePersistentObjects.add(immediatePersistentObject);
		this.immediatePersistentShadows.add(metaDataValue);
		this.addArenaObject(immediatePersistentObject, metaDataValue);
		String[] roleDatas;
		if(immediatePersistentObject instanceof RoleSelectionObject) {
			List<RoleData> roleDatasList = ((RoleSelectionObject) immediatePersistentObject).getAllRoles();
			roleDatas = new String[roleDatasList.size() * 2];
			for(int i = 0; i < roleDatasList.size(); i++) {
				roleDatas[i * 2] = roleDatasList.get(i).getRole().getName();
				roleDatas[i * 2 + 1] = String.valueOf(roleDatasList.get(i).getCounter());
			}
		} else {
			roleDatas = ArrayUtils.EMPTY_STRING_ARRAY;
		}
		this.data.add(new ObjectDataPacket(immediatePersistentObject, roleDatas));
		return true;
	}

	@Override
	public boolean registerPersistent(DelayedPersistentObject delayedPersistentObject, Time time, boolean override) {
		String metaDataValue = this.generateRandomMetaData();
		this.delayedPersistentObjects.add(delayedPersistentObject);
		this.delayedPersistentShadows.add(metaDataValue);
		this.addArenaObject(delayedPersistentObject, metaDataValue);
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
		for(String link : this.linkedObjects) {
			if(!links.contains(link)) {
				return false;
			}
		}
		return true;
	}

	public boolean removeArenaObject(String metaDataValue) {
		if(this.objectShadows.contains(metaDataValue)) {
			int index = this.objectShadows.indexOf(metaDataValue);
			ArenaObject arenaObject = this.objects.get(index);
			if(arenaObject instanceof RandomSpawnableObject) {
				int shadowIndex = this.randomSpawnableShadows.indexOf(metaDataValue);
				String groupName = this.randomSpawnableGroupShadows.get(shadowIndex);
				((RandomTimelineGroup) this.randomSpawnables.get(groupName)).remove((RandomSpawnableObject) arenaObject);
				this.randomSpawnableShadows.remove(shadowIndex);
				this.randomSpawnableGroupShadows.remove(shadowIndex);
			} else if(arenaObject instanceof FixedSpawnableObject) {
				int shadowIndex = this.fixedSpawnableShadows.indexOf(metaDataValue);
				this.fixedSpawnables.remove(shadowIndex);
				this.fixedSpawnableShadows.remove(shadowIndex);
			} else if(arenaObject instanceof ImmediatePersistentObject) {
				int shadowIndex = this.immediatePersistentShadows.indexOf(metaDataValue);
				this.immediatePersistentObjects.remove(shadowIndex);
				this.immediatePersistentShadows.remove(shadowIndex);
			} else if(arenaObject instanceof DelayedPersistentObject) {
				int shadowIndex = this.delayedPersistentShadows.indexOf(metaDataValue);
				this.delayedPersistentObjects.remove(shadowIndex);
				this.delayedPersistentShadows.remove(shadowIndex);
			} else if(arenaObject instanceof SpawnPoint) {
				int shadowIndex = this.spawnerShadows.indexOf(metaDataValue);
				this.spawners.remove(shadowIndex);
				this.spawnerShadows.remove(shadowIndex);
			}

			if(this.linkedObjectsShadows.contains(metaDataValue)) {
				int shadowIndex = this.linkedObjectsShadows.indexOf(metaDataValue);
				this.linkedObjects.remove(shadowIndex);
				this.linkedObjectsShadows.remove(shadowIndex);
			}

			this.objectShadows.remove(index);
			for(BlockState blockState : arenaObject.getAffectedBlocks()) {
				blockState.removeMetadata(ToolBarListener.RANDOM_PASS_KEY, CodariI.INSTANCE);
			}
			arenaObject.hide();
			this.objects.remove(index);
			this.data.remove(index);
			return true;
		}
		return false;
	}

	private void addArenaObject(ArenaObject object, String metaDataValue) {
		if(((LibraryCore) Codari.getLibrary()).getLinks(object.getName()) != null) {
			this.linkedObjects.add(object.getName());
			this.linkedObjectsShadows.add(metaDataValue);
		}
		//Add metadata
		for(BlockState blockState : object.getAffectedBlocks()) {
			blockState.setMetadata(ToolBarListener.RANDOM_PASS_KEY, new FixedMetadataValue(CodariI.INSTANCE, metaDataValue));
		}
		this.objects.add(object);
		this.objectShadows.add(metaDataValue);
	}

	private String generateRandomMetaData() {
		return RandomStringUtils.random(16);
	}

	//-----Random Timeline Group-----//
	private final static class RandomTimelineGroup extends TimedAction {
		private final static Random globalRandom = new Random(System.currentTimeMillis());

		//-----Fields-----//
		private final List<RandomSpawnableObject> bagOfMarbles;
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
				RandomSpawnableObject marble = this.bagOfMarbles.get(i);
				if (!marble.isSpawned()) {
					marble.spawn();
				}
			}
		}

		public void remove(RandomSpawnableObject obj) {
			while (this.bagOfMarbles.contains(obj)) {
				this.bagOfMarbles.remove(obj);
			}
		}

		private void addObject(RandomSpawnableObject object) {
			for (int i = 0; i < object.getWeight(); i++) {
				this.bagOfMarbles.add(object);
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

	@Override
	public void addSpawnLocation(SpawnPoint spawnPoint) {
		String metaDataValue = this.generateRandomMetaData();
		this.spawners.add(new SerializableLocation(spawnPoint.getLocation()));
		this.spawnerShadows.add(metaDataValue);
		this.addArenaObject(spawnPoint, metaDataValue);
		this.data.add(new ObjectDataPacket(spawnPoint));
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
		//TODO
		ConfigurationInput input = new ConfigurationInput(args);
		ArenaManagerCore arenaManager = (ArenaManagerCore) Codari.getArenaManager();
		System.out.println("Deserialization - Arena Manager found");
		GameRule gameRule = arenaManager.getGameRule(input.getString("GameRule"));
		String name = input.getString("name");
		ArenaBuilderCore builder = (ArenaBuilderCore) arenaManager.getArenaBuider(name, gameRule);
		System.out.println("Deserialization - ArenaBuilder found");
		List<RandomTimelineGroup> randomTimelineGroups = input.get(new TimelineGroupInputFunction());
		for (RandomTimelineGroup g : randomTimelineGroups) {
			builder.randomSpawnables.put(g.name, g);
		}
		System.out.println("Arena Builder - Random Timeline Groups Deserialized");
		List<ObjectDataPacket> dataList = input.get(new DataInputFunction());
		for (ObjectDataPacket data : dataList) {
			data.apply(builder);
		}
		System.out.println("Arena Builder - Object Data Packets Applied");
		for (int i = 0; args.containsKey("role_" + i); i++) {
			String rName = input.getString("role_" + i);
			arenaManager.submitRole(name, ((CodariCore) CodariI.INSTANCE).getRoleManager().getRole(rName));
		}
		System.out.println("Arena Builder - Roles Deserialized");
		//Submitting Kit to the Kit Manager
		((CodariCore) CodariI.INSTANCE).getKitManager().createKit(builder);
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
			ArenaObject arenaObject;
			if(!objectName.equals(RoleSelectionObject.OBJECT_NAME)) {
				arenaObject = ((LibraryCore) Codari.getLibrary()).createObject(objectName, this.location.getLocation());
			} else {
				Map<String, RoleData> roleDatas = new HashMap<>();
				for(int i = 0; i < extraInformation.size(); i += 2) {
					Role role = ((CodariCore) CodariI.INSTANCE).getRoleManager().getRole(extraInformation.get(i));
					int amount = Integer.parseInt(extraInformation.get(i + 1));
					if(role != null && amount > 0) {
						roleDatas.put(role.getName(), new RoleData(role, amount));
					}
				}
				builder.registerPersistent(new RoleSelectionObject(location.getLocation(), roleDatas));
				return;
			}
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
			} else if(arenaObject instanceof SpawnPoint) {
				builder.addSpawnLocation((SpawnPoint) arenaObject);
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
