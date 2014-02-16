package com.codari.arenacore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;

import com.codari.api5.CodariI;
import com.codari.api5.util.reflect.ReflectionException;
import com.codari.api5.util.reflect.Reflector;
import com.codari.arena5.Library;
import com.codari.arena5.arena.rules.Argument;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.arena.rules.timedaction.TimedActionName;
import com.codari.arena5.arena.rules.wincondition.WinCondition;
import com.codari.arena5.arena.rules.wincondition.WinConditionName;
import com.codari.arena5.item.ItemAssetName;
import com.codari.arena5.item.assets.ItemAsset;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillName;

public class LibraryCore implements Library {
	private final Map<String, Class<? extends ArenaObject>> objects;
	//private final Map<String, Class<? extends RoleDeclaration>> declarations;
	private final Map<String, Class<? extends TimedAction>> actions;
	private final Map<String, Class<? extends WinCondition>> conditions;
	private final Map<String, Class<? extends Skill>> skills;
	private final Map<String, Set<String>> links;
	private final Map<String, Class<? extends ItemAsset>> itemAssets;
	
	public LibraryCore() {
		this.objects = new HashMap<>();
		//this.declarations = new HashMap<>();
		this.actions = new HashMap<>();
		this.conditions = new HashMap<>();
		this.skills = new HashMap<>();
		this.links = new HashMap<>();
		this.itemAssets = new HashMap<>();
	}
	
	//----ArenaObject Related----//
	@Override
	public void registerArenaObject(Class<? extends ArenaObject> clazz) {	//FIXME - Add in Links
		ArenaObjectName objectName = clazz.getAnnotation(ArenaObjectName.class);
		if (objectName == null) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Missing arena object name for " + clazz);
			return;
		}
		String name = objectName.value();
		String[] links = objectName.links();
		if (this.conditions.containsKey(name)) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Arena object named " + name + " already exists");
			return;
		}
		if(links.length > 0) {
			this.links.put(name, new HashSet<String>(Arrays.asList(links)));
		}
		this.objects.put(name, clazz);
	}
	
	public ArenaObject createObject(String name, Location location) {
		Class<? extends ArenaObject> clazz = this.objects.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (ArenaObject) Reflector.invokeConstructor(clazz, location).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create arena object named " + name, ex);
			return null;
		}
	}
	
	public Collection<String> getObjectNames() {
		return this.objects.keySet();
	}
	
	public Set<Entry<String, Class<? extends ArenaObject>>> getObjectEntrys() {
		return Collections.unmodifiableSet(this.objects.entrySet());
	}
	
	/*/-----Role Declaration Related-----//
	@Override
	public void registerRoleDeclaration(Class<? extends RoleDeclaration> clazz) {
		RoleDeclarationName declarationName = clazz.getAnnotation(RoleDeclarationName.class);
		if (declarationName == null) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Missing role declaration name for " + clazz);
			return;
		}
		String name = declarationName.value();
		if (this.conditions.containsKey(name)) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Role declaration named " + name + " already exists");
			return;
		}
		this.declarations.put(name, clazz);
	}
	
	public RoleDeclaration createRoleDeclaration(String name, Object... args) {
		Class<? extends RoleDeclaration> clazz = this.declarations.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (RoleDeclaration) Reflector.invokeConstructor(clazz, args).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create role declaration named " + name, ex);
			return null;
		}
	}
	
	public Argument[] RoleDeclarationArguments(String name) {
		Class<? extends RoleDeclaration> clazz = this.declarations.get(name);
		if (clazz == null) {
			return new Argument[]{};
		}
		RoleDeclarationName declarationName = clazz.getAnnotation(RoleDeclarationName.class);
		return declarationName.constructorArguments();
	}
	
	public Collection<String> getDeclarationNames() {
		return this.declarations.keySet();
	}
	
	//-----Timed Action Related-----/*/
	@Override
	public void registerTimedAction(Class<? extends TimedAction> clazz) {
		TimedActionName actionName = clazz.getAnnotation(TimedActionName.class);
		if (actionName == null) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Missing timed action name for " + clazz);
			return;
		}
		String name = actionName.value();
		if (this.conditions.containsKey(name)) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Timed Action named " + name + " already exists");
			return;
		}
		this.actions.put(name, clazz);
	}
	
	public TimedAction createTimedAction(String name) {
		Class<? extends TimedAction> clazz = this.actions.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (TimedAction) Reflector.invokeConstructor(clazz).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create timed action named " + name, ex);
			return null;
		}
	}
	
	public TimedAction createTimedAction(String name, Object... args) {
		Class<? extends TimedAction> clazz = this.actions.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (TimedAction) Reflector.invokeConstructor(clazz, args).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create timed action named " + name, ex);
			return null;
		}
	}
	
	public Argument[] TimedActionArguments(String name) {
		Class<? extends TimedAction> clazz = this.actions.get(name);
		if (clazz == null) {
			return new Argument[]{};
		}
		TimedActionName actionName = clazz.getAnnotation(TimedActionName.class);
		return actionName.constructorArguments();
	}
	
	public Collection<String> getActionNames() {
		return this.actions.keySet();
	}
	
	//-----Win Condition Related-----//
	@Override
	public void registerWinCondition(Class<? extends WinCondition> clazz) {
		WinConditionName conditionName = clazz.getAnnotation(WinConditionName.class);
		if (conditionName == null) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Missing win condition name for " + clazz);
			return;
		}
		String name = conditionName.value();
		if (this.conditions.containsKey(name)) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Win condition named " + name + " already exists");
			return;
		}
		this.conditions.put(name, clazz);
	}
	
	public WinCondition createWinCondition(String name, Object... args) {
		Class<? extends WinCondition> clazz = this.conditions.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (WinCondition) Reflector.invokeConstructor(clazz, args).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create win condition named " + name, ex);
			return null;
		}
	}
	
	public Argument[] getWinConditionArguments(String name) {
		Class<? extends WinCondition> clazz = this.conditions.get(name);
		if (clazz == null) {
			return new Argument[]{};
		}
		WinConditionName conditionName = clazz.getAnnotation(WinConditionName.class);
		return conditionName.constructorArguments();
	}
	
	public Collection<String> getConditionNames() {
		return this.conditions.keySet();
	}
	
	//-----Skills-----//
	@Override
	public void registerSkill(Class<? extends Skill> clazz) {
		SkillName skillName = clazz.getAnnotation(SkillName.class);	
		if (skillName == null) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Missing skill name for " + clazz);
			return;
		}
		String name = skillName.value();
		if (this.skills.containsKey(name)) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Skill named " + name + " already exists");
			return;
		}
		this.skills.put(name, clazz);
	}
	
	public Skill createSkill(String name) {
		Class<? extends Skill> clazz = this.skills.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (Skill) Reflector.invokeConstructor(clazz).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create skill named " + name, ex);
			return null;
		}
	}
	
	public Collection<String> getSkillNames() {
		return this.skills.keySet();
	}
	
	//-----Link-----//
	public Set<String> getArenaObjectsWithLinks() {
		return this.links.keySet();
	}
	
	public Set<String> getLinks(String arenaObjectName) {
		if(this.links.containsKey(arenaObjectName)) {
			return this.links.get(arenaObjectName);
		}
		return null;
	}

	//-----Item Assets-----//
	@Override
	public void registerItemAsset(Class<? extends ItemAsset> clazz) {
		ItemAssetName itemAssetName = clazz.getAnnotation(ItemAssetName.class);
		if(itemAssetName == null) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Missing Item Asset Annotation for " + clazz);
			return;
		}
		String name = itemAssetName.name();
		if(this.itemAssets.containsKey(name)) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Item asset " + name + " already exists");
			return;
		}
		this.itemAssets.put(name, clazz);
	}

	public ItemAsset createItemAsset(String name) {
		Class<? extends ItemAsset> clazz = this.itemAssets.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (ItemAsset) Reflector.invokeConstructor(clazz).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create Item Asset named " + name, ex);
			return null;
		}
	}
}
