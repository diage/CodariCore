package com.codari.arenacore;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.codari.api5.CodariI;
import com.codari.arena5.Arena;
import com.codari.arena5.players.teams.Team;

public class QueueCore {
	//-----Fields-----//
	private Arena arena;
	private int arenaTeamSize;
	private List<Team> teams;
	private boolean matchStarting;
	private BukkitTask task;
	private int countDown;
	private static int countDownStartingValue = 10;

	//-----Constructor-----//
	public QueueCore(Arena arena) {
		this.arena = arena;
		this.arenaTeamSize = arena.getGameRule().getTeamSize();
		this.teams = new ArrayList<Team>();
		this.countDown = countDownStartingValue;
	}

	//-----Public Methods-----//
	public boolean addTeamToQueue(Team team) {
		if(checkIfMatchIsNotInProgress(this.arena, team) &&		//check if the match is not already in progress
				checkTeamSize(this.arena, team) &&				//check if the player's team size matches the arena's team size
				checkIfTeamIsNotAlreadyInAnArena(team) &&	//check to make sure a team doesn't join two arenas at the same time
				checkIfArenaHasAvailableSlots(this.arena, team, teams.size())) {
			this.teams.add(team);
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
		}
		this.teams.remove(team);
		return true;
	}

	public void removeAllTeamsFromQueue() {
		for(int i = 0; i < this.teams.size(); i++) {
			this.teams.remove(i--);
		}
	}

	private void startArena() {	
		this.arena.start((Team[]) this.teams.toArray());
	}

	private void countDown() {		
		this.task = Bukkit.getScheduler().runTaskTimer(CodariI.INSTANCE, new Runnable() {

			@Override
			public void run() {
				for(Team team : teams) {
					for(Player player : team.getPlayers()) {
						player.sendMessage("Match is starting in " + countDown + " seconds.");
					}
				}
				countDown--;
				if(countDown == 0) {
					startArena();
					matchStarting = false;
					countDown = countDownStartingValue;
					task.cancel();
				}
			}

		}, 0, 20);		
	}

	private void checkIfMatchShouldStart() {
		if(this.teams.size() == this.arenaTeamSize) {
			this.matchStarting = true;
			this.countDown();
		}		
	}	

	//-----Static Methods-----//
	private static boolean checkIfMatchIsNotInProgress(Arena arena, Team team) {
		if(arena.isMatchInProgress()) {
			for(Player player : team.getPlayers()) {
				player.sendMessage(ChatColor.RED + "The arena you're trying to join is already in progress.");
			}
			return false;
		}
		return true;
	}

	private static boolean checkTeamSize(Arena arena, Team team) {
		int teamSize = team.getTeamSize();
		int arenaTeamSize = arena.getGameRule().getTeamSize();
		if(teamSize != arenaTeamSize) {
			for(Player player : team.getPlayers()) {
				player.sendMessage(ChatColor.RED + "You're team has to have " + arena.getGameRule().getTeamSize() + " players to join that arena!");
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

	private static boolean checkIfArenaHasAvailableSlots(Arena arena, Team team, int numberOfTeamsInQueue) {
		int arenaSlots = arena.getGameRule().getTeamSize();
		if(numberOfTeamsInQueue == arenaSlots) {
			for(Player player : team.getPlayers()) {
				player.sendMessage(ChatColor.RED + "The arena " + arena.getName() + " is already full!");
			}
			return false;
		}	
		return true;
	}
}
