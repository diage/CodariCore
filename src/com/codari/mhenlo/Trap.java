package com.codari.mhenlo;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;

import com.codari.arena.objects.spawnable.RandomSpawnableObject;

public interface Trap extends RandomSpawnableObject {
	public void set();
	public void deactivate();
	public void trigger(List<Entity> targets);
	public Listener getListener();
	
}
