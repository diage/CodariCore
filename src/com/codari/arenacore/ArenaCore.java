package com.codari.arenacore;

import java.util.HashMap;
import java.util.Map;

import com.codari.api.util.Tick;
import com.codari.arena.Arena;
import com.codari.arena.objects.persistant.DelayedPersistentObject;
import com.codari.arena.objects.persistant.ImmediatePersistentObject;
import com.codari.arena.objects.spawnable.FixedSpawnableObject;
import com.codari.arena.objects.spawnable.RandomSpawnableObject;
import com.codari.arena.players.teams.Team;
import com.codari.arena.players.teams.TeamColor;
import com.codari.arena.rules.GameRule;

public final class ArenaCore implements Arena {
	//-----Fields-----//
	private final String name;
	private final Map<TeamColor, Team> teams;
	private final Map<String, TimelineGroup> randomTimelineGroups;
	
	//-----Constructor-----//
	public ArenaCore(String name) {
		this.name = name;
		this.teams = new HashMap<>();
		this.randomTimelineGroups = new HashMap<>();
	}
	
	//-----Public Methods-----//
	@Override
	public String getName() {
		return this.name;
	}
}