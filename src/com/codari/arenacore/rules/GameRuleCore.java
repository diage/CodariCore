package com.codari.arenacore.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.codari.api5.util.Tick;
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
	
	@Override
	public boolean addWinCondition(WinCondition winCondition, Time time, boolean after) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<WinCondition> getWinConditions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTeamSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMatchDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addRoleDelegation(RoleDelegation roleDelegation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RoleDelegation getRoleDelegation() {
		// TODO Auto-generated method stub
		return null;
	}
}