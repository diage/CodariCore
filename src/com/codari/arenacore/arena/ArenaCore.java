package com.codari.arenacore.arena;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

import com.codari.api5.CodariI;
import com.codari.api5.io.CodariSerialization;
import com.codari.api5.util.SerializableLocation;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.arena.events.ArenaEndEvent;
import com.codari.arena5.arena.events.ArenaStartEvent;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arena5.arena.rules.roledelegation.RoleDeclaration;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.arena.rules.wincondition.WinCondition;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.teams.TeamCore;

public final class ArenaCore implements Arena {
	private static final long serialVersionUID = -177761288968831586L;
	//-----Fields-----//
	private final String name;
	private final GameRule rules;
	private final List<TimedAction> actions;
	private final List<ArenaObject> objects;
	private final List<SerializableLocation> spawns;
	private transient Map<String, Team> teams;
	private transient Set<BukkitTask> tasks;
	
	//-----Constructors-----//
	public ArenaCore(String name, ArenaBuilderCore builder) {
		this.name = name;
		this.rules = builder.getGameRule();
		this.actions = builder.compileActions();
		this.objects = builder.compileObjects();
		this.spawns = builder.compileSpawners();
		this.teams = new LinkedHashMap<>();
		this.tasks = new HashSet<>();
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.teams = new LinkedHashMap<>();
		this.tasks = new HashSet<>();
		for(RoleDeclaration roleDeclaration : this.rules.getRoleDeclaration()) {
			roleDeclaration.initalizeRoles();
		}
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
	
	public void serializeTest(File file) {
		CodariSerialization.serialize(file, this);
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
	public boolean start(Team... teams) {
		Bukkit.broadcastMessage(ChatColor.RED + "Attempting to start Arena...");
		if (!this.isMatchInProgress()) {
			if (ArrayUtils.isEmpty(teams)) {
				return false;
			}
			
			if (!(teams.length == this.spawns.size())) {//TODO
				Bukkit.broadcastMessage(ChatColor.DARK_RED + "Incorrect number of spawns!");
				return false;
			}
			Bukkit.broadcastMessage(ChatColor.YELLOW + "Passed basic checks...");
			
			for (Team team : teams) {
				if(!(team.getTeamSize() == this.rules.getTeamSize())) {
					Bukkit.broadcastMessage(ChatColor.DARK_RED + "ALERT! Not enough players for team "+ team.getTeamName() + "!");
					this.teams.clear();
					return false;
				}
				this.teams.put(team.getTeamName(), team);
				if(!((TeamCore) team).setArena(this)) {
					this.teams.clear();
					Bukkit.broadcastMessage(ChatColor.DARK_RED + "ALERT! Failed to set arena for team " + team.getTeamName() + "!");
					return false;
				}
			}
			Bukkit.broadcastMessage("" + ChatColor.GREEN + this.teams.size() + " teams have been added to the arena...");
			
			ArenaStartEvent e = new ArenaStartEvent(this);
			Bukkit.getPluginManager().callEvent(e);
			if (e.isCancelled()) {
				this.teams.clear();
				return false;
			}
			Bukkit.broadcastMessage(ChatColor.BLUE + "Event successfully called...");
			
			for (WinCondition winCond : this.rules.getWinConditions()) {
				winCond.initialize(this);
				Bukkit.broadcastMessage(ChatColor.AQUA + "WinCondition " + winCond.getClass().getSimpleName() + " succesfully initialized...");
			}
			
			for (TimedAction action : this.actions) {
				long delay, period;
				delay = action.getDelay() != null ? action.getDelay().ticks() : 1l;
				period = action.getPeriod() != null ? action.getPeriod().ticks() : 0l;
				
				this.tasks.add(Bukkit.getScheduler().runTaskTimer(CodariI.INSTANCE, action, delay, period));
			}
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Arena fully started!");
			
			return true;
		}
		return false;
	}
	
	@Override
	public void stop() {
		Bukkit.broadcastMessage(ChatColor.DARK_RED + "ALERT: Match attempting to end!");
		if (this.isMatchInProgress()) {
			Bukkit.broadcastMessage(ChatColor.RED + "First check passed...");
			ArenaEndEvent e = new ArenaEndEvent(this);
			Bukkit.getPluginManager().callEvent(e);
			Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "Event called...");
			Iterator<Entry<String, Team>> teamsIterator = this.teams.entrySet().iterator();
			for (Entry<String, Team> entry; teamsIterator.hasNext();) {
				entry = teamsIterator.next();
				((TeamCore) entry.getValue()).setArena(null);
				teamsIterator.remove();
				for (Combatant combatant : entry.getValue().combatants()) {
					combatant.setHotbarActibe(false);
				}
			}
			Bukkit.broadcastMessage("" + ChatColor.DARK_GRAY + this.teams.size() + " teams remain after clearing...");
			for (ArenaObject o : this.objects) {
				o.hide();
			}
			Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "Objects removed...");
			for (BukkitTask task : this.tasks) {
				task.cancel();
			}
			Bukkit.broadcastMessage("Arena successfully ended!");
		}
	}
	
	@Override
	public boolean isMatchInProgress() {
		return !this.teams.isEmpty();
	}
}