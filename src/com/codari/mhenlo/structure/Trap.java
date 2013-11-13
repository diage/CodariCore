package com.codari.mhenlo.structure;

import java.util.List;

import org.bukkit.entity.Entity;

import com.codari.arena.objects.spawnable.RandomSpawnableObject;
import com.codari.arena.players.teams.Team;

public interface Trap extends RandomSpawnableObject {
	public void set();
	public void deactivate();
	public void trigger(List<Entity> targets);
	public void setTeam(Team team);
	public Team getTeam();	
}
