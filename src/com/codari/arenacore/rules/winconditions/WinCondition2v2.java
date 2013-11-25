package com.codari.arenacore.rules.winconditions;

import com.codari.arena5.Arena;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.rules.WinCondition;

public class WinCondition2v2 extends WinCondition {
	//-----Fields------//
	private int numberOfPoints = 0;
	private final int NUMBER_OF_POINTS_TO_WIN;
	
	public WinCondition2v2(int numberOfPointsToWin) {
		this.NUMBER_OF_POINTS_TO_WIN = numberOfPointsToWin;
	}
	
	public void incrementPoints(Arena arena, Team team, int points) {
		this.numberOfPoints += points;
		if(numberOfPoints >= NUMBER_OF_POINTS_TO_WIN) {
			this.conditionMet = true;
		}
	}
}
