package com.codari.arenacore;

import org.bukkit.scheduler.BukkitRunnable;

import com.codari.api5.Codari;
import com.codari.api5.util.Time;
import com.codari.arena5.ArenaTimeable;

public final class ArenaTimer extends BukkitRunnable {
	//-----Fields-----//
	private final ArenaTimeable timeable;
	private final Time delay, period;
	
	//-----Constructor-----//
	public ArenaTimer(ArenaTimeable timeable, Time delay, Time period) {
		this.timeable = timeable;
		this.delay = delay;
		this.period = period;
	}
	
	public ArenaTimer(ArenaTimeable timeable, Time delay) {
		this(timeable, delay, Time.NULL);
	}
	
	public void start() {
		super.runTaskTimer(Codari.INSTANCE, this.delay.ticks(), this.period.ticks());
	}
	
	@Override
	public void run() {
		this.timeable.action();
	}
}