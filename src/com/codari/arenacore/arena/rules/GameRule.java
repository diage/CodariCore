package com.codari.arenacore.arena.rules;

import java.util.Collection;

import com.codari.api5.util.Time;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.arena.rules.wincondition.WinCondition;

/**
 * Game rules will be static objects inserted into the time line upon registration. They will define passive actions, 
 * 	win conditions, and the total Match Duration. 
 * 
 * To be a valid GameRule, it must contain at least one win condition and a match duration. 
 * @author Ryan
 *
 */
public interface GameRule {
	
	public String getName();
	
	/**
	 * Method to declare the valid team size which must be met before the game will accept a team. 
	 * 
	 * @param teamSize The size to set teams to for this game rule. 
	 */
	public void setTeamSize(byte teamSize); //DONE
	
	/**
	 * Add a condition such that if it is ever met, the game will immediately end and declare victors.
	 * 
	 * @param winCondition The condition to be added.
	 */
	public void addWinCondition(WinCondition winCondition); //DONE - PARTIALLY
	
	/**
	 * Add a win condition which is only valid before or after a certain point in time.
	 * 
	 * @param winCondition The condition to be added. 
	 * @param time The time which either before or after it the win condition is valid.
	 * @param after If this true, then the win condition will be valid after the time, otherwise it will be valid before the time. 
	 * @return True if the win condition can possibly be met before the end of the game, false otherwise. 
	 */
	public boolean addWinCondition(WinCondition winCondition, Time time, boolean after); //DONE - PARTIALLY
	
	/**
	 * Set the duration of the match. Setting this more than once will overwrite the previous Match Duration.
	 * 
	 * @param time The time at which to end. 
	 */
	public void setMatchDuration(Time time); //DONE
	
	/**
	 * Set the duration of the match to be infinite. If a Time is already registered, it will be removed.
	 * 
	 * 	Registering a time after this will set it to that time. 
	 */
	public void setMatchDurationInfinite(); //DONE
	
	/**
	 * Add a passive action to happen along the time line of the arena. 
	 * @param action The Timed Action to be performed. 
	 * @return True if the action was able to have it and it's first execution before the designated end time. False otherwise.
	 * 
	 */
	public boolean addTimedAction(TimedAction action);
	
	/**
	 * Check to see if a duration and at least one win condition have been set.
	 * 	Only a valid Game Rule can be added to the arena.
	 * 
	 * @return True if the duration is set and at least one win condition exists, false otherwise. 
	 */
	public boolean isValid(); //DO NOT CONSIDER
	
	/**
	 * Method to get all of the {@link TimedAction}s for this GameRule
	 * 
	 * @return A collection of the TimedActions
	 */
	public Collection<TimedAction> getTimedActions(); //DO NOT CONSIDER
	
	/**
	 * Method to get all of the {@link WinCondition}s for this GameRule
	 *  
	 * @return A collection of WinConditions
	 */
	public Collection<WinCondition> getWinConditions(); //DO NOT CONSIDER
	
	/**
	 * Method to get the TeamSize that has been set for this GameRule.
	 *   
	 * @return The TeamSize for this GameRule
	 */
	public byte getTeamSize(); //DO NOT CONSIDER
	
	/**
	 * Method to get the MatchDuration for this GameRule
	 * 
	 * @return The MatchDuration for this GameRule, for infinite length it will return a 0.
	 */
	public Time getMatchDuration(); //DO NOT CONSIDER
	
	public void setNumberOfTeams(byte numberOfTeams); //DONE
	
	public byte getNumberOfTeams(); //DO NOT CONSIDER
}
