package com.codari.arenacore.arena.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.codari.api5.CodariI;
import com.codari.api5.util.Time;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.arena.events.ArenaWinEvent;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arena5.arena.rules.roledelegation.RoleDeclaration;
import com.codari.arena5.arena.rules.roledelegation.RoleDelegation;
import com.codari.arena5.arena.rules.timedaction.TimedAction;
import com.codari.arena5.arena.rules.wincondition.WinCondition;
import com.codari.arena5.arena.rules.wincondition.WinConditionTemplate;
import com.codari.arena5.players.combatants.Combatant;

public class GameRuleCore implements GameRule, ConfigurationSerializable {
	//-----Fields-----//
	private final String name;
	private Time matchDuration;
	private byte teamSize, numberOfTeams;
	private final List<WinCondition> winConditions;
	private final List<TimedAction> timedActions;
	private RoleDelegation roleDelegation;
	//private List<RoleDeclaration> roleDeclarations;
	
	//-----Constructor-----//
	public GameRuleCore(String name) {
		this.name = name;
		this.winConditions = new ArrayList<>();
		this.timedActions = new ArrayList<>();
		//this.roleDeclarations = new ArrayList<>();
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
	public void addWinCondition(WinCondition winCondition) {
		this.addWinCondition(winCondition, Time.NULL, true);
	}
	
	@Override
	public boolean addWinCondition(final WinCondition winCondition, Time time, boolean after) {
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
	public boolean addTimedAction(TimedAction action) {
		return this.timedActions.add(action);
	}
	
	@Override
	public void removeTimedAction(TimedAction action) {
		this.timedActions.remove(action);
	}
	
	@Override
	public boolean isValid() {
		return this.matchDuration != null && !this.winConditions.isEmpty() 
				&& this.numberOfTeams > 1 && this.teamSize > 1
				&& this.numberOfTeams < 16 && this.teamSize < 16;
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

	/*
	@Override
	public boolean addRoleDeclaration(RoleDeclaration roleDeclaration) {
		roleDeclaration.initalizeRoles();
		this.roleDeclarations.add(roleDeclaration);
		return true; 	//FIXME - improve overall role declaration
	}

	@Override
	public List<RoleDeclaration> getRoleDeclaration() {
		return this.roleDeclarations;
	}
	*/

	@Override
	public void setNumberOfTeams(byte numberOfTeams) {
		this.numberOfTeams = numberOfTeams; 
	}

	@Override
	public byte getNumberOfTeams() {
		return this.numberOfTeams;
	}

	@Override
	@Deprecated
	public boolean addRoleDeclaration(RoleDeclaration roleDeclaration) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Deprecated
	public List<RoleDeclaration> getRoleDeclaration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("name", this.name);
		result.put("match_duration", this.matchDuration);
		result.put("team_size", this.teamSize);
		result.put("number_of_teams", this.numberOfTeams);
		return result;
	}
}
