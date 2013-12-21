package com.codari.arenacore.players.builders.kit;

import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arenacore.arena.ArenaBuilderCore;

public class Kit implements Listener {
	private String name;
	private ArenaBuilder arenaBuilder;
	
	public Kit(String name, GameRule gameRule) {
		this.name = name;
		this.arenaBuilder = new ArenaBuilderCore(gameRule);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Arena buildArena() {
		return Codari.getArenaManager().buildArena(name, this.arenaBuilder);
	}
}
