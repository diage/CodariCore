package com.codari.mhenlo;

import org.bukkit.event.Listener;

import com.codari.arena.objects.spawnable.RandomSpawnableObject;

public interface Trap extends RandomSpawnableObject, Listener {
	public void set();
	public void deactivate();
}
