package com.codari.arenacore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.arena.ArenaStatics;
import com.codari.arena5.Arena;
import com.codari.arena5.ArenaStartEvent;
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
	private transient Map<String, Team> teams;
	private transient Set<BukkitTask> tasks;
	
	//-----Constructors-----//
	public ArenaCore(String name, ArenaBuilderCore builder) {
		this.name = name;
		this.rules = builder.getGameRule();
		this.actions = builder.compileActions();
		this.teams = new HashMap<>();
		this.tasks = new HashSet<>();
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.teams = new HashMap<>();
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
	
	@Override
	public boolean start(Team... teams) {
		if (!this.isMatchInProgress()) {
			if (ArrayUtils.isEmpty(teams)) {
				return false;
			}
			for (Team team : teams) {
				((TeamCore) team).setArena(this);
				this.teams.put(team.getTeamName(), team);
				Bukkit.broadcastMessage(team.combatants().get(0).getPlayer().getName());
				Bukkit.broadcastMessage(team.combatants().get(1).getPlayer().getName());
				team.combatants().get(0).setRole(Codari.getArenaManager().getExistingRole("2v2", ArenaStatics.MELEE));
				team.combatants().get(1).setRole(Codari.getArenaManager().getExistingRole("2v2", ArenaStatics.RANGED));
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
				this.tasks.add(Bukkit.getScheduler().runTaskTimer(CodariI.INSTANCE, action,
						action.getDelay() != null ? action.getDelay().ticks() : 1l,
						action.getPeriod() != null ? action.getPeriod().ticks() : 0l));
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void stop() {
		if (this.isMatchInProgress()) {
			Iterator<Entry<String, Team>> teamsIterator = this.teams.entrySet().iterator();
			for (Entry<String, Team> entry; teamsIterator.hasNext();) {
				entry = teamsIterator.next();
				((TeamCore) entry.getValue()).setArena(null);
				teamsIterator.remove();
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
}