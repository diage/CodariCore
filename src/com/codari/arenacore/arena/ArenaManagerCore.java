package com.codari.arenacore.arena;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.codari.api5.io.CodariSerialization;
import com.codari.apicore.CodariCore;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.arena.ArenaManager;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.rules.GameRuleCore;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.combatants.CombatantDataCore;
import com.codari.arenacore.players.role.RoleCore;
import com.codari.arenacore.players.teams.TeamCore;
import com.codari.arenacore.players.teams.queue.QueueCore;

public class ArenaManagerCore implements ArenaManager {
	//-----Fields-----//
	private final Map<String, Combatant> combatants;
	private final Map<String, ArenaCore> arenas;
	private final Map<String, ArenaBuilderCore> arenaBuilders;
	private final Map<String, ArenaRoleGroup> roleGroups;
	private final Map<String, QueueCore> queues;
	private final Map<String, GameRuleCore> gameRules;
	
	//-----Constructor-----//
	public ArenaManagerCore() {
		this.combatants = new HashMap<>();
		this.arenas = new HashMap<>();
		this.arenaBuilders = new HashMap<>();
		this.roleGroups = new HashMap<>();
		this.queues = new HashMap<>();
		this.gameRules = new LinkedHashMap<>();
		ConfigurationSerialization.registerClass(CombatantDataCore.class);
		ConfigurationSerialization.registerClass(ArenaBuilderCore.class);
		ConfigurationSerialization.registerClass(ArenaBuilderCore.ObjectDataPacket.class);
	}
	
	//-----Public Methods-----//
	//----Combatant Related----//
	@Override
	public Combatant getCombatant(String name) {
		name = name.toLowerCase();
		Combatant combatant = this.combatants.get(name);
		if (combatant == null) {
			combatant = new CombatantCore(name);
			this.combatants.put(name, combatant);
		}
		return combatant;
	}
	
	@Override
	public Combatant getCombatant(OfflinePlayer player) {
		return this.getCombatant(player.getName());
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
	
	public Set<String> getArenaNames() {
		return this.arenas.keySet();
	}
	
	public boolean containsArena(String arenaName) {
		return this.arenas.containsKey(arenaName);
	}
	
	@Override
	public boolean addToQueue(String arenaName, Team team) {
		if(!this.queues.containsKey(arenaName)) {
			team.getPlayers().get(0).sendMessage(ChatColor.BLUE + "The arena " + arenaName + " is null!");
		}
		((TeamCore) team).setArenaQueueName(arenaName);
		return this.queues.get(arenaName).addTeamToQueue(team);
	}
	
	@Override
	public boolean removeFromQueue(Team team) {
		String arenaName = ((TeamCore) team).getArenaQueueName();
		((TeamCore) team).setArenaQueueName(null);
		if(!this.queues.containsKey(arenaName)) {
			Bukkit.broadcastMessage(ChatColor.RED + "Trying to remove a team that's not in queue from a queue.");	//TODO
		}
		return this.queues.get(arenaName).removeTeamFromQueue(team);
	}
	
	public QueueCore getQueue(String arenaName) {
		return this.queues.get(arenaName);
	}
	
	@Override
	public Arena buildArena(String requestedName, ArenaBuilder arenaBuilder) {
		ArenaCore arena = new ArenaCore(requestedName, (ArenaBuilderCore) arenaBuilder);
		this.arenas.put(requestedName, arena);
		this.queues.put(requestedName, new QueueCore(arena));
		for(Combatant combatant : this.combatants.values()) {
			((CombatantCore) combatant).getDynamicMenuManager().addArenaIcons(requestedName);
		}
		return arena;
	}
	
	public Arena loadArena(File file) {
		ArenaCore arena = (ArenaCore) CodariSerialization.deserialize(file);
		this.arenas.put(arena.getName(), arena);
		this.queues.put(arena.getName(), new QueueCore(arena));
		for(Combatant combatant : this.combatants.values()) {
			((CombatantCore) combatant).getDynamicMenuManager().addArenaIcons(arena.getName());
		}
		return arena;
	}

	@Override
	public ArenaBuilder getArenaBuider(GameRule gameRule) {
		return new ArenaBuilderCore(gameRule);
	}

	public void addArenaBuilder(String arenaName, ArenaBuilder arenaBuilder) {
		this.arenaBuilders.put(arenaName, (ArenaBuilderCore) arenaBuilder);
	}
	
	public ArenaBuilderCore getArenaBuilder(String arenaName) {
		return this.arenaBuilders.get(arenaName);
	}
	
	public boolean containsArenaBuilder(String arenaName) {
		return this.arenaBuilders.containsKey(arenaName);
	}
	
	public void registerGameRule(String key, GameRuleCore gameRule) {
		if (this.gameRules.containsKey(key)) {
			throw new IllegalArgumentException("Game rule with that name already exists");
		}
		this.gameRules.put(key, gameRule);
	}
	
	public GameRuleCore getGameRule(String key) {
		return this.gameRules.get(key);
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

	@Override
	public boolean saveArenaBuilder(String name, File file) {
		if (!this.arenaBuilders.containsKey(name)) {
			CodariCore.instance().getLogger().log(Level.WARNING, "No arena builder saved under the name " + name);
			return false;
		}
		CodariSerialization.serialize(file, this.getArenaBuilder(name));
		return true;
	}

	@Override
	public boolean loadArenaBuilder(String name, File file) {
		if (this.arenaBuilders.containsKey(name)) {
			CodariCore.instance().getLogger().log(Level.WARNING, "Can not load arena builder to the name "
					+ name + " as a arena builder already exists with that name");
			return false;
		}
		ArenaBuilderCore arenaBuilder = (ArenaBuilderCore) CodariSerialization.deserialize(file);
		this.arenaBuilders.put(name, arenaBuilder);
		return true;
	}
}