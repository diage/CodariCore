package com.codari.arenacore.arena;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.util.SerializableLocation;
import com.codari.apicore.CodariCore;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.arena.events.ArenaEndEvent;
import com.codari.arena5.arena.events.ArenaStartEvent;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.arena.rules.wincondition.WinCondition;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.objects.RoleSelectionObject;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.teams.TeamCore;

public final class ArenaCore implements Arena {
	private static final long serialVersionUID = -177761288968831586L;
	//-----Fields-----//
	private final String name;
	private final GameRule rules;
	private final List<TimedAction> actions;
	private final List<ArenaObject> objects;
	private final List<ImmediatePersistentObject> immediatePersistentObjects;
	private final List<RoleSelectionObject> roleSelectionObjects;
	private final List<SerializableLocation> spawns;
	private transient Map<String, Team> teams;
	private transient Set<BukkitTask> tasks;
	
	private int warmUpPeriodTime;
	private BukkitTask task;
	private int countDown;
	
	//-----Constructors-----//
	public ArenaCore(String name, ArenaBuilderCore builder) {
		this.name = name;
		this.rules = builder.getGameRule();
		this.actions = builder.compileActions();
		this.objects = builder.compileObjects();
		this.immediatePersistentObjects = this.setupImmediatePersistentObjects(this.objects);
		this.roleSelectionObjects = this.setupRoleSelectionObjects(this.immediatePersistentObjects);
		this.spawns = builder.compileSpawners();
		this.teams = new LinkedHashMap<>();
		this.tasks = new HashSet<>();
		this.warmUpPeriodTime = 25;
		this.countDown = this.warmUpPeriodTime;
	}
	
	private List<ImmediatePersistentObject> setupImmediatePersistentObjects(final List<ArenaObject> arenaObjects) {
		List<ImmediatePersistentObject> immediatePersistentObjects = new ArrayList<>();
		for(ArenaObject arenaObject : arenaObjects) {
			if(arenaObject instanceof ImmediatePersistentObject) {
				immediatePersistentObjects.add((ImmediatePersistentObject) arenaObject);
			}
		}
		return immediatePersistentObjects;
	}
	
	private List<RoleSelectionObject> setupRoleSelectionObjects(final List<ImmediatePersistentObject> immediatePersistentObjects) {
		List<RoleSelectionObject> roleSelectionObjects = new ArrayList<>();
		for(ImmediatePersistentObject immediatePersistentObject : immediatePersistentObjects) {
			if(immediatePersistentObject instanceof RoleSelectionObject) {
				roleSelectionObjects.add((RoleSelectionObject) immediatePersistentObject);
			}	
		}
		return roleSelectionObjects;
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.teams = new LinkedHashMap<>();
		this.tasks = new HashSet<>();
	}
	
	//-----Public Methods-----//
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Map<String, Team> getTeams() {
		return new HashMap<String, Team>(this.teams);
	}
	
	@Override
	public GameRule getGameRule() {
		return this.rules;
	}
	
	public Location getSpawn(Combatant combatant) {
		if (!this.isMatchInProgress()) {
			return null;
		}
		Team team = combatant.getTeam();
		int i = 0;
		Location result = null;
		for (Entry<String, Team> teamEntry : this.teams.entrySet()) {
			if (teamEntry.getValue().equals(team)) {
				result = this.spawns.get(i).getLocation();
				break;
			}
			i++;
		}
		return result;
	}
	
	@Override
	public void start(Team... teams) {
		this.warmUpPeriod(teams);
	}
	
	private void warmUpPeriod(final Team... teams) {
		this.revealAndActivateImmediatePersistentObjects();
		if(this.task == null) {
			this.task = Bukkit.getScheduler().runTaskTimer(CodariI.INSTANCE, new Runnable() {
				@Override
				public void run() {
					for(Team team : teams) {
						for(Player player : team.getPlayers()) {
							if(countDown == warmUpPeriodTime) {
								player.sendMessage("Warmup Period - " + warmUpPeriodTime + " seconds");
							} 
							player.sendMessage("[" + countDown + "]");
						}
					}
					countDown--;
					if(countDown <= 0) {
						countDown = warmUpPeriodTime;
						startArena(teams);
						hideRoleSelectionObjects();
						assignRolesIfPlayerDidntPickOne(teams);
						task.cancel();
						task = null;
					}
				}

			}, 0, 20);
		}
	}
	
	private boolean startArena(Team... teams) {
		if (!this.isMatchInProgress()) {
			if (ArrayUtils.isEmpty(teams)) {
				return false;
			}
			
			if (!(teams.length == this.spawns.size())) {
				Bukkit.broadcastMessage(ChatColor.DARK_RED + "Incorrect number of spawns!");
				return false;
			}
			
			for (Team team : teams) {
				if(!(team.getTeamSize() == this.rules.getTeamSize())) {
					this.teams.clear();
					return false;
				}
				this.teams.put(team.getTeamName(), team);
				if(!((TeamCore) team).setArena(this)) {
					this.teams.clear();
					return false;
				}
			}
			
			ArenaStartEvent e = new ArenaStartEvent(this);
			Bukkit.getPluginManager().callEvent(e);
			if (e.isCancelled()) {
				this.teams.clear();
				return false;
			}
			
			for (WinCondition winCond : this.rules.getWinConditions()) {
				winCond.initialize(this);
			}
			
			for (TimedAction action : this.actions) {
				long delay, period;
				delay = action.getDelay() != null ? action.getDelay().ticks() : 1l;
				period = action.getPeriod() != null ? action.getPeriod().ticks() : 0l;
				
				this.tasks.add(Bukkit.getScheduler().runTaskTimer(CodariI.INSTANCE, action, delay, period));
			}
			
			return true;
		}
		return false;
	}
	
	@Override
	public void stop() {
		if (this.isMatchInProgress()) {
			ArenaEndEvent e = new ArenaEndEvent(this);
			Bukkit.getPluginManager().callEvent(e);
			Iterator<Entry<String, Team>> teamsIterator = this.teams.entrySet().iterator();
			for (Entry<String, Team> entry; teamsIterator.hasNext();) {
				entry = teamsIterator.next();
				((TeamCore) entry.getValue()).setArena(null);
				teamsIterator.remove();
				for (Combatant combatant : entry.getValue().combatants()) {
					combatant.setHotbarActibe(false);
				}
			}
			for (ArenaObject o : this.objects) {
				o.hide();
			}
			for (BukkitTask task : this.tasks) {
				task.cancel();
			}
		}
	}
	
	@Override
	public boolean isMatchInProgress() {
		return !this.teams.isEmpty();
	}
	
	private void revealAndActivateImmediatePersistentObjects() {
		for(ImmediatePersistentObject immediatePersistentObject : this.immediatePersistentObjects) {
			immediatePersistentObject.reveal();
			immediatePersistentObject.activate();
		}
	}
	
	private void hideRoleSelectionObjects() {
		for(RoleSelectionObject roleSelectionObject : this.roleSelectionObjects) {
			roleSelectionObject.hide();
		}
	}
	
	private void assignRolesIfPlayerDidntPickOne(Team... teams) {
		for(Team team : teams) {
			for(Player player : team.getPlayers()) {
				Combatant combatant = Codari.getArenaManager().getCombatant(player);
				if(combatant.getRole().getName().equals(CombatantCore.NON_COMBATANT)) {
					List<String> roleNames = new ArrayList<>();
					roleNames.addAll(((ArenaManagerCore) Codari.getArenaManager()).getExistingRoleNames(this.name));
					int size = roleNames.size();
					Random random = new Random(System.currentTimeMillis());
					String roleName = roleNames.get(random.nextInt(size));
					Role role = ((CodariCore) CodariI.INSTANCE).getRoleManager().getRole(roleName);
					if(role != null) {				
						combatant.setRole(role);
						player.sendMessage(ChatColor.AQUA + "You have been assigned to the " + role.getName() + " role.");
					} else {
						Bukkit.broadcastMessage(ChatColor.RED + "Player failed to be assigned a role because the roll is null!"); //TODO - for testing
					}
				}	//FIXME - the roles are supposed to be randomly chosen from the remaining role selection object roles if there is one
			}		//FIXME - Role Selection Objects needs teams to do this
		}			//FIXME - Method to get remaining roles from Role Selection Object is .getRemainingRoles()
	}
}
