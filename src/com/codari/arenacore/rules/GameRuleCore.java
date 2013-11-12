package com.codari.arenacore.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.codari.api.util.Tick;
import com.codari.arena.rules.GameRule;
import com.codari.arena.rules.TimedAction;
import com.codari.arena.rules.WinCondition;

public class GameRuleCore implements GameRule {
	//-----Fields-----//
	private final List<WinCondition> winConditions;
	private Tick matchDuration;
	private final Set<TimedAction> timedActions;
	
	//-----Constructor-----//
	public GameRuleCore() {
		this.winConditions = new ArrayList<>();
		this.timedActions = new HashSet<>();
	}
	
	@Override
	public void addWinCondition(WinCondition winCondition) {
		this.winConditions.add(winCondition);
	}

	@Override
	public boolean addWinCondition(WinCondition winCondition, Tick time, boolean after) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeWinCondition(WinCondition winCondition) {
		this.winConditions.remove(winCondition);
	}

	@Override
	public void setMatchDuration(Tick time) {
		this.matchDuration = time;
	}

	@Override
	public void setMatchDurationInfinite() {
		this.matchDuration = new Tick(Long.MAX_VALUE);
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
	public void setTeamSize(int teamSize) {
		// TODO Auto-generated method stub
		
	}
}