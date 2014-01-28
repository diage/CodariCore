package com.codari.arenacore.arena.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.util.Time;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.arena.events.ArenaWinEvent;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.arena.rules.wincondition.WinCondition;
import com.codari.arena5.arena.rules.wincondition.WinConditionTemplate;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.arena.ArenaManagerCore;

public class GameRuleCore implements GameRule, ConfigurationSerializable {
	//-----Fields-----//
	public final static int MAX_NUMBER_OF_TEAMS = 16;
	public final static int MIN_NUMBER_OF_TEAMS = 2;
	public final static int MAX_NUMBER_OF_PLAYERS_PER_TEAM = 16;
	public final static int MIN_NUMBER_OF_PLAYERS_PER_TEAM = 1; //FIXME - Min. Team size allowed to be 1 for now to making testing easier
	
	private final String name;
	private Time matchDuration;
	private byte teamSize, numberOfTeams;
	private final List<WinCondition> winConditions;
	private final List<TimedAction> timedActions;
	private final List<DataStuff> dataStuff;
	
	//-----Constructor-----//
	public GameRuleCore(String name) {
		this.name = name;
		this.winConditions = new ArrayList<>();
		this.timedActions = new ArrayList<>();
		this.dataStuff = new ArrayList<>();
	}
	
	//-----Public Methods-----//
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setTeamSize(byte teamSize) {
		this.teamSize = teamSize;
	}
	
	@Override
	@Deprecated
	public void addWinCondition(WinCondition winCondition) {
		this.addWinCondition(winCondition, Time.NULL, true);
	}
	
	@Override
	@Deprecated
	public boolean addWinCondition(final WinCondition winCondition, Time time, boolean after) {
		
		return false;
	}
	
	public boolean addWinCondition(Time time, boolean after, String name, Object... args) {
		final WinCondition winCondition = ((LibraryCore) Codari.getLibrary()).createWinCondition(name, args);
		if (this.addTimedAction(new WinConditionAction(time, (WinConditionTemplate) winCondition, after))) {
			this.timedActions.add(new TimedAction(null, Time.ONE_TICK, Time.ONE_TICK) {
				private static final long serialVersionUID = -3268071058821069399L;
				@Override
				public void action() {
					if (winCondition.conditionMet()) {
						Collection<Combatant> winners = winCondition.getWinners();	
						Arena arena = null;
						for (Combatant c : winners) {
							if (c.getTeam().getArena() != null) {
								arena = c.getTeam().getArena();
								Bukkit.broadcastMessage(ChatColor.DARK_GREEN + c.getTeam().getTeamName() + " has won the " + arena.getName() + " arena!");
								break;
							}
						}
						if (arena == null) {
							return;
						}
						Bukkit.getPluginManager().callEvent(new ArenaWinEvent(arena, winners));
						arena.stop();
					}
				}
			});
			this.winConditions.add(winCondition);
			this.dataStuff.add(new WinConditionDataStuff(name, args, time, after));
			return true;
		}
		return false;
	}
	
	@Override
	public void setMatchDuration(Time time) {
		this.matchDuration = time;
	}
	
	@Override
	public void setMatchDurationInfinite() {
		this.matchDuration = Time.MAX;
	}
	
	@Override
	public boolean addTimedAction(TimedAction action) {
		return this.timedActions.add(action);
	}
	
	public boolean addTimedAction(String name, Object... args) {
		TimedAction action = ((LibraryCore) Codari.getLibrary()).createTimedAction(name, args);
		this.timedActions.add(action);
		this.dataStuff.add(new TimeActionDataStuff(name, args));
		return false;
	}
	
	@Override
	public boolean isValid() {
		return this.matchDuration != null && !this.winConditions.isEmpty() 
				&& this.numberOfTeams >= MIN_NUMBER_OF_TEAMS && this.teamSize >= MIN_NUMBER_OF_PLAYERS_PER_TEAM	
				&& this.numberOfTeams <= MAX_NUMBER_OF_TEAMS && this.teamSize <= MAX_NUMBER_OF_PLAYERS_PER_TEAM;
	}

	@Override
	public Collection<TimedAction> getTimedActions() {
		return this.timedActions;
	}

	@Override
	public Collection<WinCondition> getWinConditions() {
		return this.winConditions;
	}

	@Override
	public byte getTeamSize() {
		return this.teamSize;
	}

	@Override
	public Time getMatchDuration() { 
		return this.matchDuration;
	}
	
	//-----Win Condition Action-----//
	private final static class WinConditionAction extends TimedAction {
		private static final long serialVersionUID = 5391314571661514449L;
		//-----Fields-----//
		private final WinConditionTemplate winCond;
		private final boolean after;
		private final Time delay;
		
		public WinConditionAction(Time delay, WinConditionTemplate winCond, boolean after) {
			super(null, Time.ONE_TICK);
			this.winCond = winCond;
			this.after = after;
			this.delay = delay;
		}

		@Override
		public void action() {
			this.winCond.setRegistered(!after);
			Bukkit.getScheduler().runTaskLater(CodariI.INSTANCE, new Runnable() {
				@Override
				public void run() {
					winCond.setRegistered(after);
				}
			}, delay.ticks());
		}
	}

	@Override
	public void setNumberOfTeams(byte numberOfTeams) {
		this.numberOfTeams = numberOfTeams; 
	}

	@Override
	public byte getNumberOfTeams() {
		return this.numberOfTeams;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("name", this.name);
		result.put("match_duration", this.matchDuration);
		result.put("team_size", this.teamSize);
		result.put("number_of_teams", this.numberOfTeams);
		for (int i = 0; i < this.dataStuff.size(); i++) {
			result.put("data_stuff_" + i, this.dataStuff.get(i));
		}
		return result;
	}
	
	public static GameRuleCore deserialize(Map<String, Object> args) {
		args = new HashMap<>(args);
		GameRuleCore rule = new GameRuleCore((String) args.remove("name"));
		rule.setMatchDuration((Time) args.remove("match_duration"));
		rule.setTeamSize((byte) args.remove("team_size"));
		rule.setNumberOfTeams((byte) args.remove("number_of_teams"));
		//TODO
		System.out.println("POTATO DEBUG!!!!! GAME DATA 1");
		for (int i = 0; i < args.size(); i++) {
			((DataStuff) args.get("data_stuff_" + i)).apply(rule);
		}
		System.out.println("POTATO DEBUG!!!!! GAME DATA 2");
		((ArenaManagerCore) Codari.getArenaManager()).registerGameRule(rule);
		System.out.println("POTATO DEBUG!!!!! GAME DATA 3");
		return rule;
	}
	
	//-----Data Stuff-----//
	private static interface DataStuff extends ConfigurationSerializable {
		public void apply(GameRuleCore rule);
	}
	
	@SerializableAs("Time_Action_Data_Stuff")
	public static class TimeActionDataStuff implements DataStuff {
		//-----Fields-----//
		private final String name;
		private final Object[] args;
		
		//-----Constructor-----//
		private TimeActionDataStuff(String name, Object[] args) {
			this.name = name;
			this.args = args;
		}
		
		@Override
		public Map<String, Object> serialize() {
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("name", this.name);
			for (int i = 0; i < args.length; i++) {
				result.put("arg_" + i, args[i]);
			}
			return result;
		}
		
		public TimeActionDataStuff(Map<String, Object> args) {
			//TODO
			System.out.println("POTATO DEBUG!!!!! TIME DATA 1");
			args = new HashMap<>(args);
			System.out.println("POTATO DEBUG!!!!! TIME DATA 2");
			this.name = (String) args.remove("name");
			System.out.println("POTATO DEBUG!!!!! TIME DATA 3");
			this.args = new Object[args.size()];
			System.out.println("POTATO DEBUG!!!!! TIME DATA 4");
			for (int i = 0; i < this.args.length; i++) {
				this.args[i] = args.get("arg_" + i); 
			}
			System.out.println("POTATO DEBUG!!!!! TIME DATA 5");
		}

		@Override
		public void apply(GameRuleCore rule) {
			rule.addTimedAction(name, args);
		}
	}
	
	@SerializableAs("Win_Condition_Data_Stuff")
	public static class WinConditionDataStuff implements DataStuff {
		//-----Fields-----//
		private final String name;
		private final Object[] args;
		private final Time time;
		private final boolean after;
		
		//-----Constructor-----//
		private WinConditionDataStuff(String name, Object[] args, Time time, boolean after) {
			this.name = name;
			this.args = args;
			this.time = time;
			this.after = after;
		}
		
		//-----Constructor-----//
		@Override
		public Map<String, Object> serialize() {
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("name", this.name);
			result.put("time", time);
			result.put("after", after);
			for (int i = 0; i < args.length; i++) {
				result.put("arg_" + i, args[i]);
			}
			return result;
		}
		
		public WinConditionDataStuff(Map<String, Object> args) {
			//TODO
			System.out.println("POTATO DEBUG!!!!! WIN DATA 1");
			args = new HashMap<>(args);
			System.out.println("POTATO DEBUG!!!!! WIN DATA 2");
			this.name = (String) args.remove("name");
			System.out.println("POTATO DEBUG!!!!! WIN DATA 3");
			this.time = (Time) args.remove("time");
			System.out.println("POTATO DEBUG!!!!! WIN DATA 4");
			this.after = (boolean) args.remove("after");
			System.out.println("POTATO DEBUG!!!!! WIN DATA 5");
			this.args = new Object[args.size()];
			System.out.println("POTATO DEBUG!!!!! WIN DATA 6");
			for (int i = 0; i < this.args.length; i++) {
				this.args[i] = args.get("arg_" + i); 
			}
			System.out.println("POTATO DEBUG!!!!! WIN DATA 7");
		}

		@Override
		public void apply(GameRuleCore rule) {
			rule.addWinCondition(time, after, name, args);
		}
	}
}
