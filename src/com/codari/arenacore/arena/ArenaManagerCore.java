package com.codari.arenacore.arena;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.codari.api5.io.CodariSerialization;
import com.codari.api5.util.Tick;
import com.codari.api5.util.Time;
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
	//private final Map<String, ArenaGroupCore> arenaGroups;
	private final Map<String, ArenaBuilderCore> arenaBuilders;
	private final Map<String, ArenaRoleGroup> roleGroups;
	private final Map<String, QueueCore> queues;
	private final Map<String, GameRuleCore> gameRules;
	private final File builderDir;
	private final File roleDir;
	
	//-----Constructor-----//
	public ArenaManagerCore() {
		this.combatants = new HashMap<>();
		this.arenas = new HashMap<>();
		//this.arenaGroups = new HashMap<>();
		this.arenaBuilders = new HashMap<>();
		this.roleGroups = new HashMap<>();
		this.queues = new HashMap<>();
		this.gameRules = new LinkedHashMap<>();
		ConfigurationSerialization.registerClass(CombatantDataCore.class);
		ConfigurationSerialization.registerClass(ArenaBuilderCore.class);
		ConfigurationSerialization.registerClass(ArenaBuilderCore.ObjectDataPacket.class);
		ConfigurationSerialization.registerClass(Tick.class);
		ConfigurationSerialization.registerClass(Time.class);
		ConfigurationSerialization.registerClass(GameRuleCore.class);
		ConfigurationSerialization.registerClass(GameRuleCore.TimeActionDataStuff.class);
		ConfigurationSerialization.registerClass(GameRuleCore.WinConditionDataStuff.class);
		this.builderDir = new File(CodariCore.instance().getDataFolder(), "DEM_BUILDERS");
		this.roleDir = new File(CodariCore.instance().getDataFolder(), "ALL DA RULZ");
		if (this.roleDir.exists()) {
			for (File file : this.roleDir.listFiles()) {
				try {
					this.loadGameRule(file);
				} catch (Exception ex) {
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "Couldnt find potato in " + file, ex);
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				}
			}
		} else {
			this.roleDir.mkdirs();
		}
		if (this.builderDir.exists()) {
			for (File file : this.builderDir.listFiles()) {
				try {
					this.loadArena(file);
				} catch (Exception ex) {
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "Couldnt find potato in " + file, ex);
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				}
			}
		} else {
			this.builderDir.mkdirs();
		}
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
	
	public Collection<Combatant> getCombatants() {
		return this.combatants.values();
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
		return new RoleCore(name, null);
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
	
	public boolean hasAnExistingRole(String arenaName) {
		if(this.roleGroups.containsKey(arenaName)) {
			return this.roleGroups.get(arenaName).getRoleNames().size() > 0;
		}
		return false;
	}
	
	public Collection<String> getExistingRoleNames(String arenaName) {
		if(this.roleGroups.containsKey(arenaName)) {
			return this.roleGroups.get(arenaName).getRoleNames();
		}
		return null;
	}
	
	//----Arena Builder Related----//
	@Override
	public ArenaCore getArena(String name) {
		return this.arenas.get(name);
	}
	
	public Map<String, ArenaCore> getArenaList() {
		return new LinkedHashMap<>(this.arenas);
	}
	
	public boolean containsArena(String arenaName) {
		return this.arenas.containsKey(arenaName);
	}
	
	@Override
	public boolean addToQueue(String arenaName, Team team) {
		if(!this.queues.containsKey(arenaName)) {
			team.getPlayers().get(0).sendMessage(ChatColor.BLUE + "The arena " + arenaName + " is null!");
		}
		((TeamCore) team).setQueue(this.queues.get(arenaName));
		return ((TeamCore) team).getQueue().addTeamToQueue(team);
	}
	
	@Override
	public boolean removeFromQueue(Team team) {
		QueueCore queue = ((TeamCore) team).getQueue();
		if(queue != null) {
			((TeamCore) team).setQueue(null);
			return queue.removeTeamFromQueue(team);		
		}
		Bukkit.broadcastMessage(ChatColor.RED + "Trying to remove a team that's not in queue from a queue.");	//TODO
		return false;
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
			((CombatantCore) combatant).getDynamicMenuManager().addArenaIcon(requestedName);
			combatant.getPlayer().sendMessage(ChatColor.GREEN + "An Arena has been added to the Menu!");
		}
		return arena;
	}
	
	public Arena loadArena(File file) {
		ArenaCore arena = (ArenaCore) CodariSerialization.deserialize(file);
		this.arenas.put(arena.getName(), arena);
		this.queues.put(arena.getName(), new QueueCore(arena));
		for(Combatant combatant : this.combatants.values()) {
			((CombatantCore) combatant).getDynamicMenuManager().addArenaIcon(arena.getName());
			combatant.getPlayer().sendMessage(ChatColor.GREEN + "An Arena has been added to the Menu!");
		}
		return arena;
	}

	@Override
	public ArenaBuilder getArenaBuider(GameRule gameRule) {
		return new ArenaBuilderCore((GameRuleCore) gameRule);
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
	
	public Set<Entry<String, ArenaBuilderCore>> getArenaBuilders() {
		return this.arenaBuilders.entrySet();
	}
	
	public void registerGameRule(GameRuleCore gameRule) {
		if (this.gameRules.containsKey(gameRule.getName())) {
			throw new IllegalArgumentException("Game rule with that name already exists");
		}
		this.gameRules.put(gameRule.getName(), gameRule);
	}
	
	public GameRuleCore getGameRule(String key) {
		return this.gameRules.get(key);
	}
	
	public Set<Entry<String, GameRuleCore>> getGameRules() {
		return this.gameRules.entrySet();
	}

	public class ArenaRoleGroup {
		private Map<String, Role> roleGroup;
		private static final String NON_COMBATANT = "Non Combatant";
		
		public ArenaRoleGroup() {
			this.roleGroup = new HashMap<>(); 
			this.roleGroup.put(NON_COMBATANT, new RoleCore(NON_COMBATANT, null));
		}
		
		public ArenaRoleGroup(Role role) {
			this.roleGroup = new HashMap<>(); 
			this.roleGroup.put(NON_COMBATANT, new RoleCore(NON_COMBATANT, null));
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
		
		public Collection<String> getRoleNames() {
			List<String> roleNames = new ArrayList<>();
			for(String roleName : this.roleGroup.keySet()) {
				if(!roleName.equals(NON_COMBATANT)) {
					roleNames.add(roleName);
				}
			}
			return roleNames;
		}
		
		public void removeRole(String roleName) {
			this.roleGroup.remove(roleName);
		}
	}
	
	public void saveArenaBuilders() {
		for (Entry<String, ArenaBuilderCore> e : this.arenaBuilders.entrySet()) {
			try {
				File file = new File(this.builderDir, e.getKey());
				CodariSerialization.serialize(file, e.getValue());
			} catch (Exception ex) {
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "Couldnt save potato in " + e.getValue(), ex);
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
			}
		}
	}
	
	public void saveGameRules() {
		for (Entry<String, GameRuleCore> e : this.gameRules.entrySet()) {
			try {
				File file = new File(this.roleDir, e.getKey());
				CodariSerialization.serialize(file, e.getValue());
			} catch (Exception ex) {
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "Couldnt save potato in " + e.getValue(), ex);
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
			}
		}
	}

	@Override
	public boolean saveArenaBuilder(String name) {
		if (!this.arenaBuilders.containsKey(name)) {
			CodariCore.instance().getLogger().log(Level.WARNING, "No arena builder saved under the name " + name);
			return false;
		}
		File file = new File(this.builderDir, name);
		CodariSerialization.serialize(file, this.getArenaBuilder(name));
		return true;
	}

	@Override
	public boolean loadArenaBuilder(File file) {
		String name = file.getName();
		if (this.arenaBuilders.containsKey(name)) {
			CodariCore.instance().getLogger().log(Level.WARNING, "Can not load arena builder to the name "
					+ name + " as a arena builder already exists with that name");
			return false;
		}
		ArenaBuilderCore arenaBuilder = (ArenaBuilderCore) CodariSerialization.deserialize(file);
		this.arenaBuilders.put(name, arenaBuilder);
		return true;
	}
	
	public boolean saveGameRule(String name) {
		if (!this.gameRules.containsKey(name)) {
			CodariCore.instance().getLogger().log(Level.WARNING, "No game rule saved under the name " + name);
			return false;
		}
		File file = new File(this.roleDir, name);
		CodariSerialization.serialize(file, this.getGameRule(name));
		return true;
	}
	public boolean loadGameRule(File file) {
		String name = file.getName();
		if (this.gameRules.containsKey(name)) {
			CodariCore.instance().getLogger().log(Level.WARNING, "Can not game rule to the name "
					+ name + " as a arena builder already exists with that name");
			return false;
		}
		GameRuleCore rule = (GameRuleCore) CodariSerialization.deserialize(file);
		this.gameRules.put(name, rule);
		return true;
	}
}
