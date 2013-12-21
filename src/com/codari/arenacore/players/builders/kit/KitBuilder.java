package com.codari.arenacore.players.builders.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.api5.util.Time;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arena5.arena.rules.wincondition.WinCondition;
import com.codari.arenacore.arena.rules.GameRuleCore;

public class KitBuilder {
	private String name;
	private GameRule gameRule;

	//-----TIME-----/
	private boolean acceptingTimeChange;
	private long minutes, seconds, ticks;

	//-----WIN CONDITION-----//
	private WinCondition winCondition;
	private boolean winConditionAfterTime;
	private long winConditionMin, winConditionSec, WinConditionTick;

	public KitBuilder(String name) {
		this.name = name;
		this.acceptingTimeChange = true;
		this.gameRule = new GameRuleCore();
	}

	public String getName() {
		return this.name;
	}

	public Kit buildKit(String kitName) {
		if(this.gameRule.isValid()) {
			return new Kit(kitName, this.gameRule);
		} else {
			Bukkit.broadcastMessage(ChatColor.DARK_RED + "Warning: GameRule not valid!");
			return null;
		}
	}

	//-----TIME-----//
	public void updateMinutes(long minutes) {
		if(this.acceptingTimeChange) {
			this.minutes = minutes;
		}
	}

	public void updateSeconds(long seconds) {
		if(this.acceptingTimeChange) {
			this.seconds = seconds;
		}
	}

	public void updateTicks(long ticks) {
		if(this.acceptingTimeChange) {
			this.ticks = ticks;
		}
	}

	public boolean isAcceptingTime() {
		return this.acceptingTimeChange;
	}

	public void setTime(boolean isInfinite) {
		if(isInfinite) {
			this.gameRule.setMatchDurationInfinite();
		} else {
			this.gameRule.setMatchDuration(new Time(this.minutes, this.seconds, this.ticks));
		}
		this.acceptingTimeChange = false;
	}

	//-----TEAM SIZE & NUMBER OF TEAMS-----//
	public void setTeamSize(byte teamSize) {
		this.gameRule.setTeamSize(teamSize);
	}

	public void setNumberOfTeams(byte numberOfTeams) {
		this.gameRule.setNumberOfTeams(numberOfTeams);
	}

	//-----WIN CONDITION-----//
	public void selectWinCondition(WinCondition winCondition) {
		this.winCondition = winCondition;
		this.winConditionAfterTime = true;
		this.winConditionMin = 0;
		this.winConditionSec = 0;
		this.WinConditionTick = 0;
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

	public void submitWinCondition() {
		if(!(this.winCondition == null)) {
			if(this.winConditionMin > 0 
					|| this.winConditionSec > 0
					|| this.WinConditionTick > 0) {
				this.gameRule.addWinCondition(this.winCondition, new Time(this.winConditionMin, this.winConditionSec, this.WinConditionTick), this.winConditionAfterTime);
			} else {
				this.gameRule.addWinCondition(this.winCondition);
			}
			this.winCondition = null;
			this.winConditionAfterTime = true;
			this.winConditionMin = 0;
			this.winConditionSec = 0;
			this.WinConditionTick = 0;
		}
	}
}
