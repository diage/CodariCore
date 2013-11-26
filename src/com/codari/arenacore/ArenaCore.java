package com.codari.arenacore;

import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.Arena;
import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.players.teams.Team;


public final class ArenaCore implements Arena {
	//-----Fields-----//
	private final String name;
	private final ArenaBuilderCore builder;
	private final Map<String, Team> teams;
	
	//-----Constructors-----//
	public ArenaCore(String name, ArenaBuilderCore builder) {
		this.name = name;
		this.builder = builder;
		this.teams = new HashMap<>();
	}
	
	//-----Public Methods-----//
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Map<String, Team> getTeams() {
		return this.teams;
	}
	
	@Override
	public ArenaBuilder getArenaBuilder() {
		return this.builder;
	}
}