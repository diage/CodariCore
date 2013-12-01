package com.codari.arenacore;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Location;

import com.codari.api5.CodariI;
import com.codari.api5.util.reflect.Reflector;
import com.codari.arena5.Library;
import com.codari.arena5.LibraryRegister;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.rules.roledelegation.RoleDeclaration;
import com.codari.arena5.rules.roledelegation.RoleDeclarationName;
import com.codari.arena5.rules.timedaction.TimedAction;
import com.codari.arena5.rules.timedaction.TimedActionName;
import com.codari.arena5.rules.wincondition.WinCondition;
import com.codari.arena5.rules.wincondition.WinConditionName;

public class LibraryCore implements Library{
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

	@Override
	public ArenaObject createObject(String name, Location location) {
		Class<? extends ArenaObject> clazz = this.objects.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return Reflector.invokeConstructor(clazz, location).fetchAs(ArenaObject.class);
		} catch (SecurityException | InstantiationException | IllegalAccessException |
				IllegalArgumentException | InvocationTargetException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create arena object named " + name, ex);
			return null;
		}
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

	@Override
	public RoleDeclaration createRoleDeclaration(String name, Location location) {
		Class<? extends RoleDeclaration> clazz = this.declarations.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return Reflector.invokeConstructor(clazz, location).fetchAs(RoleDeclaration.class);
		} catch (SecurityException | InstantiationException | IllegalAccessException |
				IllegalArgumentException | InvocationTargetException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create role declaration named " + name, ex);
			return null;
		}
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

	@Override
	public TimedAction createTimedAction(String name, Location location) {
		Class<? extends TimedAction> clazz = this.actions.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return Reflector.invokeConstructor(clazz, location).fetchAs(TimedAction.class);
		} catch (SecurityException | InstantiationException | IllegalAccessException |
				IllegalArgumentException | InvocationTargetException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create timed action named " + name, ex);
			return null;
		}
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

	@Override
	public WinCondition createWinCondition(String name, Location location) {
		Class<? extends WinCondition> clazz = this.conditions.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			return Reflector.invokeConstructor(clazz, location).fetchAs(WinCondition.class);
		} catch (SecurityException | InstantiationException | IllegalAccessException |
				IllegalArgumentException | InvocationTargetException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, "Could not create win condition named " + name, ex);
			return null;
		}
	}
}
