package com.codari.arenacore.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.codari.api5.util.Time;
import com.codari.arena5.rules.GameRule;
import com.codari.arena5.rules.TimedAction;
import com.codari.arena5.rules.WinCondition;
import com.codari.arena5.rules.roles.RoleDelegation;

public class GameRuleCore implements GameRule {
	//-----Fields-----//
	private final List<WinCondition> winConditions;
	private Time matchDuration;
	private final Set<TimedAction> timedActions;
	private int teamSize;
	private RoleDelegation roleDelegation;
	
	//-----Constructor-----//
	public GameRuleCore() {
		this.winConditions = new ArrayList<>();
		this.timedActions = new HashSet<>();
	}
	
	//-----Public Methods-----//
	@Override
	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}
	
	@Override
	public void addWinCondition(WinCondition winCondition) {
		this.addWinCondition(winCondition, Time.NULL, true);
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean addWinCondition(WinCondition winCondition, Time time, boolean after) {
		this.addWinCondition(winCondition, time, after);
		if(true/* if win condition can possibly be met before the end of the game */) {
			return true;
		}
		return false; 
	}
	
	@Override
	public void removeWinCondition(WinCondition winCondition) {
		this.winConditions.remove(winCondition);
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
	public boolean addAction(TimedAction action) {
		return this.timedActions.add(action);
	}
	
	@Override
	public boolean isValid() {
		return this.matchDuration != null && !this.winConditions.isEmpty();
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
	public int getTeamSize() {
		return this.teamSize;
	}

	@Override
	public Time getMatchDuration() { 
		return this.matchDuration;
	}

	@Override
	public boolean addRoleDelegation(RoleDelegation roleDelegation) {
		if(roleDelegation.isValidRoleDelegation()) {
			this.roleDelegation = roleDelegation;
			return true;
		}
		return false;
	}

	@Override
	public RoleDelegation getRoleDelegation() {
		return this.roleDelegation;
	}
}