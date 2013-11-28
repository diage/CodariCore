package com.codari.arenacore;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.codari.api5.CodariI;
import com.codari.api5.util.PlayerReference;
import com.codari.api5.util.reflect.Reflector;
import com.codari.arena5.Arena;
import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.ArenaManager;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.rules.GameRule;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.combatants.CombatantDataCore;
import com.codari.arenacore.players.role.RoleCore;

public class ArenaManagerCore implements ArenaManager {
	//-----Fields-----//
	private final Map<String, Combatant> combatants;
	private final Map<String, ArenaCore> arenas;
	private final Map<String, Class<? extends ArenaObject>> objects;
	private final Map<String, ArenaBuilderCore> arenaBuilders;
	private final Map<String, ArenaRoleGroup> roleGroups;
	
	//-----Constructor-----//
	public ArenaManagerCore() {
		this.combatants = new HashMap<>();
		this.arenas = new HashMap<>();
		this.objects = new HashMap<>();
		this.arenaBuilders = new HashMap<>();
		this.roleGroups = new HashMap<>();
		ConfigurationSerialization.registerClass(CombatantDataCore.class);
	}
	
	//-----Public Methods-----//
	//----Combatant Related----//
	@Override
	public Combatant getCombatant(String name) {
		Combatant combatant = this.combatants.get(name);
		if (combatant == null) {
			combatant = new CombatantCore(PlayerReference.instanceOf(name));
			this.combatants.put(name, combatant);
		}
		return combatant;
	}
	
	@Override
	public Combatant getCombatant(OfflinePlayer player) {
		return this.getCombatant(player.getName());
	}
	
	@Override
	public Combatant getCombatant(PlayerReference playerReference) {
		return this.getCombatant(playerReference.getName());
	}
	
	@Override
	public Team getTeam(String arenaName, String teamName) {
		return this.getArena(arenaName).getTeams().get(teamName);
	}

	@Override
	public Team getTeam(Combatant combatant) {
		return combatant.getTeam();
	}
	
	
	//----Role Related----//
	@Override
	public Role getNewRole(String name) {
		return new RoleCore(name);
	}
	
	@Override
	public boolean submitRole(String arenaName, Role role) {
		if(this.roleGroups.containsKey(arenaName)) {
			if(!this.roleGroups.get(arenaName).contains(role)) {
				this.roleGroups.get(arenaName).addRole(role);
				return true;
			}
		} else {
			this.roleGroups.put(arenaName, new ArenaRoleGroup(role));
			return true;
		}
		return false;
	}
	
	@Override
	public void clearRole(String arenaName, String roleName) {
		if(this.roleGroups.containsKey(arenaName)) {
			this.roleGroups.get(arenaName).removeRole(roleName);
		}
	}
	
	@Override
	public Role getExistingRole(String arenaName, String roleName) {
		if(this.roleGroups.containsKey(arenaName)) {
			return this.roleGroups.get(arenaName).getRole(roleName);
		}
		return null;
	}
	
	//----Arena Builder Related----//
	@Override
	public ArenaCore getArena(String name) {
		return this.arenas.get(name);
	}
	
	@Override
	public Arena buildArena(String requestedName, ArenaBuilder arenaBuilder) {
		ArenaCore arena = new ArenaCore(requestedName, (ArenaBuilderCore) arenaBuilder);
		arenas.put(requestedName, arena);
		return arena;
	}

	@Override
	public ArenaBuilder getArenaBuider(GameRule gameRule) {
		return new ArenaBuilderCore(gameRule);
	}
	
	//TODO: What are these for??
	//VVVVVVVVVVVVVVVVV
	public void addArenaBuilder(String playerName, ArenaBuilderCore arenaBuilder) {
		this.arenaBuilders.put(playerName, arenaBuilder);
	}
	
	public ArenaBuilderCore getArenaBuilder(String playerName) {
		return this.arenaBuilders.get(playerName);
	}
	//^^^^^^^^^^^^^^^^^^
	
	//----ArenaObject Related----//
	@Override
	@Deprecated
	public void registerArenaObject(Class<? extends ArenaObject> clazz) {
		ArenaObjectName objectName = clazz.getAnnotation(ArenaObjectName.class);
		if (objectName == null) {
			throw new IllegalArgumentException("Arena Object does not contain requested object");
		}
		String name = objectName.value();
		if (objects.containsKey(name)) {
			throw new IllegalArgumentException("Arena Object named " + name + " already exists");
		}
		this.objects.put(name, clazz);
	}

	@Override
	@Deprecated
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
	
	public class ArenaRoleGroup {
		private Map<String, Role> roleGroup;
		
		public ArenaRoleGroup() {
			this.roleGroup = new HashMap<>(); 
			this.roleGroup.put("Non Combatant", new RoleCore("Non Combatant"));
		}
		
		public ArenaRoleGroup(Role role) {
			this.roleGroup = new HashMap<>(); 
			this.roleGroup.put("Non Combatant", new RoleCore("Non Combatant"));
			this.roleGroup.put(role.getName(), role);
		}
		
		public void addRole(Role role) {
			this.roleGroup.put(role.getName(), role);
		}
		
		public boolean contains(Role role) {
			return this.roleGroup.containsKey(role.getName());
		}
		
		public Role getRole(String roleName) {
			if(this.roleGroup.containsKey(roleName)) {
				return this.roleGroup.get(roleName);
			}
			return null;
		}
		public void removeRole(String roleName) {
			this.roleGroup.remove(roleName);
		}
	}
}