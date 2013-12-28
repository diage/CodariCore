package com.codari.arenacore.players.builders.kit;

import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.api5.util.Time;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.arena.ArenaBuilder;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arenacore.arena.ArenaBuilderCore;

public class Kit implements Listener {
	private String name;
	private ArenaBuilder arenaBuilder;
	
	//-----Random TimeLine Group-----//
	private String groupName;
	
	//---Time---//
	private long randomDelayMinutes, randomDelaySeconds, randomDelayTicks;
	private long randomRepeatMinutes, randomRepeatSeconds, randomRepeatTicks;
	private Time randomTimeLineDelayTime, randomTimeLineRepeatTime;
	
	//-----Fixed Spawnable-----//
	private long fixedDelayMinutes, fixedDelaySeconds, fixedDelayTicks;
	private long fixedRepeatMinutes, fixedRepeatSeconds, fixedRepeatTicks;
	private Time fixedSpawnableDelayTime, fixedSpawnableRepeatTime;	
	
	//-----Persistent-----//
	private long persistentDelayMinutes, persistentDelaySeconds, persistentDelayTicks;
	private Time persistentDelayTime;
	private boolean override;
	
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
	
	
	//-----Random TimeLine Group-----//
	public boolean createRandomTimeLineGroup() {		
		if(this.groupName == null || this.randomTimeLineDelayTime == null) {
			return false;
		}
		if(this.randomTimeLineRepeatTime == null) {
			this.arenaBuilder.createRandomTimelineGroup(this.groupName, this.randomTimeLineDelayTime);
		} else {
			this.arenaBuilder.createRandomTimelineGroup(this.groupName, this.randomTimeLineDelayTime, randomTimeLineRepeatTime);
		}
		return true;
	}

	public void createGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	//---Random Delay Time---//
	public void updateRandomDelayMinutes(long minutes) {
		this.randomDelayMinutes = minutes;
	}
	
	public void updateRandomDelaySeconds(long seconds) {
		this.randomDelaySeconds = seconds;
	}
	
	public void updateRandomDelayTicks(long ticks) {
		this.randomDelayTicks = ticks;
	}	
	
	public void createRandomDelayTime() {
		this.randomTimeLineDelayTime = new Time(this.randomDelayMinutes, this.randomDelaySeconds, this.randomDelayTicks);
	}
	
	//---Random Repeat Time---//
	public void updateRandomRepeatMinutes(long minutes) {
		this.randomRepeatMinutes = minutes;
	}
	
	public void updateRandomRepeatSeconds(long seconds) {
		this.randomRepeatSeconds = seconds;
	}
	
	public void updateRandomRepeatTicks(long ticks) {
		this.randomRepeatTicks = ticks;
	}		
	
	public void createRandomRepeatTime() {
		this.randomTimeLineRepeatTime = new Time(this.randomRepeatMinutes, this.randomRepeatSeconds, this.randomRepeatTicks);
	}
	
	//-----Fixed Spawnable-----//
	//---Fixed Delay Time---//
	public void updateFixedDelayMinutes(long minutes) {
		this.fixedDelayMinutes = minutes;
	}
	
	public void updateFixedDelaySeconds(long seconds) {
		this.fixedDelaySeconds = seconds;
	}
	
	public void updateFixedDelayTicks(long ticks) {
		this.fixedDelayTicks = ticks;
	}	
	
	public void createFixedDelayTime() {
		this.fixedSpawnableDelayTime = new Time(this.fixedDelayMinutes, this.fixedDelaySeconds, this.fixedDelayTicks);
	}	
	
	public Time getFixedDelayTime() {
		return this.fixedSpawnableDelayTime;
	}
	
	//---Fixed Repeat Time---//
	public void updateFixedRepeatMinutes(long minutes) {
		this.fixedRepeatMinutes = minutes;
	}
	
	public void updateFixedRepeatSeconds(long seconds) {
		this.fixedRepeatSeconds = seconds;
	}
	
	public void updateFixedRepeatTicks(long ticks) {
		this.fixedRepeatTicks = ticks;
	}	
	
	public void createFixedRepeatTime() {
		this.fixedSpawnableRepeatTime = new Time(this.fixedRepeatMinutes, this.fixedRepeatSeconds, this.fixedRepeatTicks);
	}	
	
	public Time getFixedRepeatTime() {
		return this.fixedSpawnableRepeatTime;
	}
	
	//-----Persistent-----//
	//---Persistent Delay Time---//	
	public void updatePersistentDelayMinutes(long minutes) {
		this.persistentDelayMinutes = minutes;
	}
	
	public void updatePersistentDelaySeconds(long seconds) {
		this.persistentDelaySeconds = seconds;
	}
	
	public void updatePersistentDelayTicks(long ticks) {
		this.persistentDelayTicks = ticks;
	}	
	
	public void createPersistentDelayTime() {
		this.persistentDelayTime = new Time(this.persistentDelayMinutes, this.persistentDelaySeconds, this.persistentDelayTicks);
	}	
	
	public Time getPersistentDelayTime() {
		return this.persistentDelayTime;
	}
	
	public void setOverride(boolean override) {
		this.override = override;
	}
	
	public boolean getOverride() {
		return this.override;
	}
}
