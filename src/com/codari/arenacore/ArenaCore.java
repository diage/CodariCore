package com.codari.arenacore;

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

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.io.CodariSerialization;
import com.codari.api5.util.SerializableLocation;
import com.codari.api5.util.scheduler.BukkitTime;
import com.codari.arena.ArenaStatics;
import com.codari.arena5.Arena;
import com.codari.arena5.ArenaEndEvent;
import com.codari.arena5.ArenaStartEvent;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.rules.GameRule;
import com.codari.arena5.rules.timedaction.TimedAction;
import com.codari.arena5.rules.wincondition.WinCondition;
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
			if (teams.length > this.spawns.size()) {//TODO
				Bukkit.broadcastMessage(ChatColor.DARK_RED + "Not enough spawns?!");
				return false;
			}
			Bukkit.broadcastMessage(ChatColor.YELLOW + "Passed basic checks...");
			for (Team team : teams) {
				((TeamCore) team).setArena(this);
				this.teams.put(team.getTeamName(), team);
				Bukkit.broadcastMessage(team.combatants().get(0).getPlayer().getName());
				Bukkit.broadcastMessage(team.combatants().get(1).getPlayer().getName());
				team.combatants().get(0).setRole(Codari.getArenaManager().getExistingRole(ArenaStatics.ARENA_NAME, ArenaStatics.MELEE));
				team.combatants().get(1).setRole(Codari.getArenaManager().getExistingRole(ArenaStatics.ARENA_NAME, ArenaStatics.RANGED));
				for (Combatant combatant : team.combatants()) {
					combatant.getPlayer().teleport(this.getSpawn(combatant));
					combatant.setHotbarCooldown(BukkitTime.SECOND.tickValueOf(1));
					combatant.setHotbarActibe(true);
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
				this.tasks.add(Bukkit.getScheduler().runTaskTimer(CodariI.INSTANCE, action,
						action.getDelay() != null ? action.getDelay().ticks() : 1l,
						action.getPeriod() != null ? action.getPeriod().ticks() : 0l));
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