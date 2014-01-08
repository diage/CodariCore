package com.codari.arenacore.players.builders.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.api5.Codari;
import com.codari.api5.util.Time;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.arena.rules.GameRule;
import com.codari.arenacore.arena.ArenaBuilderCore;

public class Kit implements Listener {
	private String name;
	private ArenaBuilderCore arenaBuilder;
	
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
	
	//-----Tool Bar-----//
	private final ItemStack[] tools;
	private final static ItemStack SPAWN_SETTER = new ItemStack(Material.STICK);
	private final static ItemStack TOOLBAR_EXIT = new ItemStack(Material.STICK);
	static {
		ItemMeta spawnMeta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
		spawnMeta.setDisplayName("Spawn Setter");
		List<String> spawnLore = new ArrayList<>();
		spawnLore.add(ChatColor.GREEN + "Spawn Locations");
		spawnMeta.setLore(spawnLore);
		SPAWN_SETTER.setItemMeta(spawnMeta);
		ItemMeta exitMeta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
		exitMeta.setDisplayName(ChatColor.RED + "Exit ToolBar");
		TOOLBAR_EXIT.setItemMeta(exitMeta);
	}
	
	public Kit(String name, GameRule gameRule) {
		this.name = name;
		this.arenaBuilder = new ArenaBuilderCore(gameRule);
		this.tools = new ItemStack[9];
		this.tools[4] = SPAWN_SETTER;
		this.tools[5] = TOOLBAR_EXIT;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArenaBuilderCore getArenaBuilder() {
		return this.arenaBuilder;
	}
	
	public void setName(String name) {
		this.name = name;
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
			this.groupName = null;
			this.randomTimeLineDelayTime = null;
		} else {
			this.arenaBuilder.createRandomTimelineGroup(this.groupName, this.randomTimeLineDelayTime, randomTimeLineRepeatTime);
			this.groupName = null;
			this.randomTimeLineDelayTime = null;
			this.randomTimeLineRepeatTime = null;
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
		Bukkit.broadcastMessage(ChatColor.GREEN + "Current random delay time - Minutes: " + this.randomDelayMinutes + " Seconds: " + this.randomDelaySeconds  + " Ticks: " + this.randomDelayTicks);	//TODO
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
	
	//-----TOOL BAR STUFF-----//
	public void setTool(int slot, String objectName, String... extraInformation) throws IllegalArgumentException {
		if (slot < 0 || slot > 3) {
			throw new IllegalArgumentException("Tool slots are between 0 and 3 only");
		}
		ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
		meta.setDisplayName(objectName);
		extraInformation = ArrayUtils.nullToEmpty(extraInformation);
		List<String> lore = Arrays.asList(extraInformation);
		meta.setLore(lore);
		ItemStack item = new ItemStack(Material.STICK);
		item.setItemMeta(meta);
		this.tools[slot] = item;
	}
	
	public ItemStack[] getTools() {
		return ArrayUtils.clone(this.tools);
	}
	
	void addSpawn(Location location) {
		addSpawn(location, this.tools[4]);
	}
	
	void addSpawn(Location location, ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lore = meta.getLore();
		lore.add(spawnString(location));
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
	}
	
	String spawnString(Location location) {
		return ChatColor.GREEN + "Spawn:" +
				" x=" + location.getBlockX() +
				" y=" + location.getBlockY() +
				" z=" + location.getBlockZ();
	}
}
