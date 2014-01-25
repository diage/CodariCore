package com.codari.arenacore.arena;

import java.util.LinkedList;
import java.util.List;

import com.codari.arena5.arena.Arena;

public class ArenaGroupCore {
	private String name;
	private List<Arena> arenas;

	public ArenaGroupCore(String name) {
		this.arenas = new LinkedList<>();
	}

	public boolean addArenaToStart(Arena arena) {
		if(arena != null) {
			this.arenas.add(0, arena);
			return true;
		}
		return false;
	}
	
	public boolean addArena(int index, Arena arena) {
		if(arena != null && index >= 0 && index <= this.arenas.size()) {
			this.arenas.add(index, arena);
		}
		return false;
	}

	public boolean addArenaToEnd(Arena arena) {
		if(arena != null) {
			this.arenas.add(arena);
			return true;
		}
		return false;
	}

	public boolean removeArena(Arena arena) {
		return this.arenas.remove(arena);
	}
	
	public boolean removeArena(int index) {
		if(index >= 0 && index < this.arenas.size()) {
			this.arenas.remove(index);
		}
		return false;
	}

	public boolean containsArena(Arena arena) {
		return this.arenas.contains(arena);
	}
	
	public String getName() {
		return this.name;
	}

	public Arena getArena(int index) {
		return this.arenas.get(index);
	}
	
	/**
	 * Gets the next arena in the Arena Group. 
	 * 
	 * @param arena
	 * @return Same arena if specified arena is the last arena of the Arena Group. Null if specified arena is null.
	 */
	public Arena getNextArena(Arena arena) {
		if(this.arenas.contains(arena)) {
			int index = this.arenas.indexOf(arena);
			if(index < this.arenas.size() - 1) {
				return this.arenas.get(index + 1);
			} 
			return arena;
		}
		return null;
	}
	
	public LinkedList<Arena> getArenas() {
		return (LinkedList<Arena>) this.arenas;
	}
}
