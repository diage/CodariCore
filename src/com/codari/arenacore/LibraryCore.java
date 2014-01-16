package com.codari.arenacore;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.api5.util.reflect.ReflectionException;
import com.codari.api5.util.reflect.Reflector;
import com.codari.arena5.Library;
import com.codari.arena5.arena.rules.roledelegation.RoleDeclaration;
import com.codari.arena5.arena.rules.roledelegation.RoleDeclarationName;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.arena.rules.timedaction.TimedActionName;
import com.codari.arena5.arena.rules.wincondition.WinCondition;
import com.codari.arena5.arena.rules.wincondition.WinConditionName;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.ArenaObjectName;

public class LibraryCore implements Library {
	private final Map<String, Class<? extends ArenaObject>> objects;
	private final Map<String, Class<? extends RoleDeclaration>> declarations;
	private final Map<String, Class<? extends TimedAction>> actions;
	private final Map<String, Class<? extends WinCondition>> conditions;
	
	public LibraryCore() {
		this.objects = new HashMap<>();
		this.declarations = new HashMap<>();
		this.actions = new HashMap<>();
		this.conditions = new HashMap<>();
	}
	
	//----ArenaObject Related----//
	@Override
	public void registerArenaObject(Class<? extends ArenaObject> clazz) {
		ArenaObjectName objectName = clazz.getAnnotation(ArenaObjectName.class);
		if (objectName == null) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Missing arena object name for " + clazz);
			return;
		}
		String name = objectName.value();
		if (this.conditions.containsKey(name)) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Arena object named " + name + " already exists");
			return;
		}
		this.objects.put(name, clazz);
	}
	
	@Deprecated
	public ArenaObject createObject(String name, Player player) {
		Class<? extends ArenaObject> clazz = this.objects.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (ArenaObject) Reflector.invokeConstructor(clazz, player.getLocation()).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create arena object named " + name, ex);
			return null;
		}
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
	
	//-----Role Declaration Related-----//
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
	
	public RoleDeclaration createRoleDeclaration(String name) {
		Class<? extends RoleDeclaration> clazz = this.declarations.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (RoleDeclaration) Reflector.invokeConstructor(clazz).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create role declaration named " + name, ex);
			return null;
		}
	}
	
	public Collection<String> getDeclarationNames() {
		return this.declarations.keySet();
	}
	
	//-----Timed Action Related-----//
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
	
	@Deprecated
	public TimedAction createTimedAction(String name, Player player) {
		Class<? extends TimedAction> clazz = this.actions.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (TimedAction) Reflector.invokeConstructor(clazz, player).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create timed action named " + name, ex);
			return null;
		}
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
	
	public WinCondition createWinCondition(String name) {
		Class<? extends WinCondition> clazz = this.conditions.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return (WinCondition) Reflector.invokeConstructor(clazz).getHandle();
		} catch (ReflectionException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create win condition named " + name, ex);
			return null;
		}
	}
	
	public Collection<String> getConditionNames() {
		return this.conditions.keySet();
	}
}
