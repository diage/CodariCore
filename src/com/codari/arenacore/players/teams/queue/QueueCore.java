package com.codari.arenacore.players.teams.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.codari.api5.CodariI;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.ArenaCore;
import com.codari.arenacore.players.teams.TeamCore;

public class QueueCore {
	//-----Fields-----//
	private Arena arena;
	private int arenaNumberOfTeams;
	private List<Team> teams;
	private boolean matchStarting;
	private BukkitTask task;
	private int countDown;
	private Map<String, Integer> previouslyDisplayedQueueTimes;
	private final static int COUNT_DOWN_STARTING_VALUE = 10;

	//-----Constructor-----//
	public QueueCore(Arena arena) {
		this.arena = arena;
		this.arenaNumberOfTeams = ((ArenaCore) arena).getGameRule().getNumberOfTeams();
		this.teams = new ArrayList<Team>();
		this.countDown = COUNT_DOWN_STARTING_VALUE;
		this.previouslyDisplayedQueueTimes = new HashMap<>();
	}

	//-----Public Methods-----//
	public boolean addTeamToQueue(Team team) {
		if(checkTeamSize(this.arena, team) &&										//check if the player's team size matches the arena's team size
				checkIfTeamIsNotAlreadyInAnArena(team) &&							//check to make sure a team doesn't join two arenas at the same time
				checkIfTeamIsNotInQueue(team)) {									//check to make sure the team is not already in a queue
			this.teams.add(team);
			((TeamCore)team).setInQueue(true);
			this.checkIfMatchShouldStart();
			return true;
		}	
		return false;
	}

	public boolean removeTeamFromQueue(Team team) {
		if(this.matchStarting) {
			for(Player player : team.getPlayers()) {
				player.sendMessage(ChatColor.RED + "Failed to leave queue: The match is starting!");
			}
			return false;
		} else if(this.teams.contains(team)) {
			((TeamCore)team).setInQueue(false);
			this.teams.remove(team);
			if(this.previouslyDisplayedQueueTimes.containsKey(team.getTeamName())) {
				this.previouslyDisplayedQueueTimes.remove(team.getTeamName());
			}
			this.displayQueuePositions();
			return true;
		}
		Bukkit.broadcastMessage(ChatColor.RED + "Failed to remove team from queue!"); //TODO
		return false;
	}

	public void removeTeamsFromQueue(Team[] teams) {
		for(Team team : teams) {
			this.removeTeamFromQueue(team);
		}
	}

	public void removeAllTeamsFromQueue() {
		Team[] teamArray =  new Team[this.teams.size()];
		this.removeTeamsFromQueue(this.teams.toArray(teamArray));
	}

	public void checkIfMatchShouldStart() {
		if(this.teams.size() >= this.arenaNumberOfTeams) {
			if(!this.matchStarting) {
				if(checkIfMatchIsNotInProgress(this.arena)) {		
					this.matchStarting = true;
					this.countDown();
				}
			} else {
				this.displayQueuePositions();	
			}
		}		
	}		

	private void startArena() {
		Team[] teamArray =  new Team[this.arenaNumberOfTeams];
		for(int i = 0; i < this.arenaNumberOfTeams; i++) {
			teamArray[i] = this.teams.get(i);
		}
		((ArenaCore) this.arena).start(teamArray);
		this.matchStarting = false;
		this.removeTeamsFromQueue(teamArray);
	}

	private void countDown() {
		if(this.task == null) {
			this.task = Bukkit.getScheduler().runTaskTimer(CodariI.INSTANCE, new Runnable() {
				@Override
				public void run() {
					for(Team team : teams) {
						for(Player player : team.getPlayers()) {
							player.sendMessage("Match is starting in [" + countDown + "] seconds.");
						}
					}
					countDown--;
					if(countDown <= 0) {
						task.cancel();
						task = null;
						startArena();
						countDown = COUNT_DOWN_STARTING_VALUE;
					}
				}

			}, 0, 20);
		}
	}

	private void displayQueuePositions() {
		for(Team team : this.teams) {
			int queuePosition = this.teams.indexOf(team);
			Integer previouslyDisplayedPosition = this.previouslyDisplayedQueueTimes.get(team.getTeamName());
			this.previouslyDisplayedQueueTimes.put(team.getTeamName(), queuePosition);
			if(previouslyDisplayedPosition == null || previouslyDisplayedPosition.intValue() != queuePosition) {
				for(Player player : team.getPlayers()) {
					player.sendMessage(ChatColor.BLUE + "Waiting for the arena to open up. Your team's queue positon is " + queuePosition);
				}
			}
		}
	}

	//-----Static Methods-----//
	private static boolean checkIfMatchIsNotInProgress(Arena arena) {
		if(((ArenaCore) arena).isMatchInProgress()) {
			return false;
		}
		return true;
	}

	private static boolean checkTeamSize(Arena arena, Team team) {
		int teamSize = team.getTeamSize();
		int arenaTeamSize = ((ArenaCore) arena).getGameRule().getTeamSize();
		if(teamSize != arenaTeamSize) {
			for(Player player : team.getPlayers()) {
				player.sendMessage(ChatColor.RED + "You're team has to have " + ((ArenaCore) arena).getGameRule().getTeamSize() + " players to join that arena!");
			}
			return false;
		}
		return true;
	}

	private static boolean checkIfTeamIsNotAlreadyInAnArena(Team team) {
		if(team.getArena() != null) {
			for(Player player : team.getPlayers()) {
				player.sendMessage(ChatColor.RED + "You can't join another arena if your team is already part of one!");
			}
			return false;
		}
		return true;
	}	

	private static boolean checkIfTeamIsNotInQueue(Team team) {
		if(((TeamCore)team).checkIfInQueue()) {
			for(Player player : team.getPlayers()) {
				player.sendMessage(ChatColor.RED + "Your team can't join another queue while your team is already part of one!");
			}
			return false;
		}
		return true;
	}
}
