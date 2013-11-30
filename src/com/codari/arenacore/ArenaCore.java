package com.codari.arenacore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.codari.api5.CodariI;
import com.codari.arena5.Arena;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.rules.GameRule;
import com.codari.arena5.rules.timedaction.TimedAction;


public final class ArenaCore implements Arena {
	//-----Fields-----//
	private final String name;
	private final GameRule rules;
	private final List<TimedAction> actions;
	private final Map<String, Team> teams;
	private final Set<BukkitTask> tasks;
	
	//-----Constructors-----//
	public ArenaCore(String name, ArenaBuilderCore builder) {
		this.name = name;
		this.rules = builder.getGameRule();
		this.actions = builder.compileActions();
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
				this.teams.put(team.getTeamName(), team);
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
			this.teams.clear();
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