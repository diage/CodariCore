package com.codari.arenacore.players.teams.queue;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.api5.events.ArenaEndEvent;
import com.codari.arenacore.arena.ArenaManagerCore;

public class QueueCoreListener implements Listener {
	@EventHandler()
	private void arenaEnd(ArenaEndEvent e) {
		((ArenaManagerCore)Codari.getArenaManager()).getQueue(e.getArena().getName()).checkIfMatchShouldStart();
	}
}
