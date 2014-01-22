package com.codari.arenacore.players.builders.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.api5.Codari;
import com.codari.api5.util.Time;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.arena.rules.wincondition.WinCondition;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.arena.rules.GameRuleCore;

public class KitBuilder {
	private String name;
	private GameRule gameRule;
	
	//-----TIME-----/
	private long minutes, seconds, ticks;
	private boolean timeSet;
	
	//-----WIN CONDITION-----//
	private WinCondition winCondition;
	private String winConditionName;
	private boolean winConditionAfterTime;
	private long winConditionMin, winConditionSec, WinConditionTick;
	private Object[] winConditionArguments;
	
	public KitBuilder(String name) {
		this.name = name;
		this.gameRule = new GameRuleCore(name);
	}
	
	/* Constructor for serialized Game Rules */
	public KitBuilder(GameRule gameRule) {
		this.name = gameRule.getName();
		this.gameRule = gameRule;
	}
	
	public String getName() {
		return this.name;
	}
	
	public GameRuleCore getGameRule() {
		return (GameRuleCore) this.gameRule;
	}

	public Kit buildKit(String kitName) {
		if(this.gameRule.isValid()) {
			return new Kit(kitName, this.gameRule);
		} else {
			Bukkit.broadcastMessage(ChatColor.DARK_RED + "Warning: GameRule not valid!");	//TODO
			return null;
		}
	}

	//-----TIME-----//
	public void updateMinutes(long minutes) {
		this.minutes = minutes;
		this.timeSet = true;
	}
	
	public long getMinutes() {
		return this.minutes;
	}

	public void updateSeconds(long seconds) {
		this.seconds = seconds;
		this.timeSet = true;
	}
	
	public long getSeconds() {
		return this.seconds;
	}

	public void updateTicks(long ticks) {
		this.ticks = ticks;
		this.timeSet = true;
	}
	
	public long getTicks() {
		return this.ticks;
	}
	
	public boolean timeSet() {
		return this.timeSet;
	}

	public void setTime(boolean isInfinite) {
		if(isInfinite) {
			this.gameRule.setMatchDurationInfinite();
		} else {
			this.gameRule.setMatchDuration(new Time(this.minutes, this.seconds, this.ticks));
		}
	}

	//-----TEAM SIZE & NUMBER OF TEAMS-----//
	public void setTeamSize(byte teamSize) {
		this.gameRule.setTeamSize(teamSize);
	}

	public void setNumberOfTeams(byte numberOfTeams) {
		this.gameRule.setNumberOfTeams(numberOfTeams);
	}

	//-----WIN CONDITION-----//
	public void selectNewWinCondition(String winConditionName) {
		this.winConditionAfterTime = true;
		this.winConditionMin = 0;
		this.winConditionSec = 0;
		this.WinConditionTick = 0;
		this.winConditionName = winConditionName;
		this.winConditionArguments = new Object[((LibraryCore) Codari.getLibrary()).getWinConditionArguments(winConditionName).length];		
	}
	
	public String getWinConditionName() {
		return this.winConditionName;
	}

	public void setWinConditionMinute(long minute) {
		this.winConditionMin = minute;
	}

	public void SetWinConditionSecond(long second) {
		this.winConditionSec = second;
	}

	public void SetWinConditionTick(long tick) {
		this.WinConditionTick = tick;
	}

	public void setWinConditionAfter(boolean after) {
		this.winConditionAfterTime = after;
	}
	
	public void addWinConditionArgument(int index, Object argument) {
		this.winConditionArguments[index] = argument;
	}
	
	public boolean winConditionArgumentsFilled() {
		for(int i = 0; i < this.winConditionArguments.length; i++) {
			if(this.winConditionArguments[i] == null) {
				return false;
			}
		}
		return true;
	}

	public void submitWinCondition() {
		this.addWinConditionArguments();
		if(!(this.winCondition == null)) {
			if(this.winConditionMin > 0 
					|| this.winConditionSec > 0
					|| this.WinConditionTick > 0) {
				this.gameRule.addWinCondition(this.winCondition, new Time(this.winConditionMin, this.winConditionSec, this.WinConditionTick), this.winConditionAfterTime);
			} else {
				this.gameRule.addWinCondition(this.winCondition);
			}
			this.winCondition = null;
			this.winConditionName = null;
			this.winConditionAfterTime = true;
			this.winConditionMin = 0;
			this.winConditionSec = 0;
			this.WinConditionTick = 0;
		}
	}
	
	//-----WIN CONDITION-----//
	public void submitTimedAction(TimedAction timedAction) {
		if(timedAction != null) {
			this.gameRule.addTimedAction(timedAction);
		}
	}
	
	public boolean isValid() {
		return this.gameRule.isValid();
	}
	
	private void addWinConditionArguments() {
		if(this.winConditionArguments.length > 0) {
			this.winCondition = ((LibraryCore) Codari.getLibrary()).createWinCondition(this.winConditionName, this.winConditionArguments);
		}
	}
}
