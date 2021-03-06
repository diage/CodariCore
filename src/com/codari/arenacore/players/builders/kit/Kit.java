package com.codari.arenacore.players.builders.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
import net.minecraft.util.org.apache.commons.lang3.builder.HashCodeBuilder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.api5.Codari;
import com.codari.api5.util.Time;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arenacore.arena.ArenaBuilderCore;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.arena.objects.RoleData;
import com.codari.arenacore.arena.rules.GameRule;
import com.codari.arenacore.arena.rules.GameRuleCore;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.role.RoleCore;

public class Kit {
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
	
	//----Role Selection-----//
	private Map<String, RoleData> roleDatas;
	
	//-----Tool Bar-----//
	private final ItemStack[] tools;
	private final static ItemStack DELETE_TOOL = new ItemStack(Material.BLAZE_ROD);
	private final static ItemStack TOOLBAR_EXIT = new ItemStack(Material.STICK);
	static {
		ItemMeta spawnMeta = Bukkit.getItemFactory().getItemMeta(Material.BLAZE_ROD);
		spawnMeta.setDisplayName("Delete Tool");
		DELETE_TOOL.setItemMeta(spawnMeta);
		ItemMeta exitMeta = Bukkit.getItemFactory().getItemMeta(Material.STICK);
		exitMeta.setDisplayName(ChatColor.RED + "Exit ToolBar");
		TOOLBAR_EXIT.setItemMeta(exitMeta);
	}
	
	public Kit(String name, GameRule gameRule) {
		this.name = name;
		this.arenaBuilder = new ArenaBuilderCore(name, (GameRuleCore) gameRule);
		this.roleDatas = new HashMap<>();
		this.tools = new ItemStack[9];
		this.tools[4] = DELETE_TOOL;
		this.tools[5] = TOOLBAR_EXIT; 
	}
	
	/* Constructor for serialized Arena Builders */
	public Kit(String name, ArenaBuilderCore arenaBuilder) {
		this.name = name;
		this.arenaBuilder = arenaBuilder;
		this.tools = new ItemStack[9];
		this.tools[4] = DELETE_TOOL;
		this.tools[5] = TOOLBAR_EXIT;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArenaBuilderCore getArenaBuilder() {
		return this.arenaBuilder;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Kit) {
			return this.getName().equals(((Kit)obj).getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getName()).build();
	}
	
	public Arena buildArena() {
		return ((ArenaManagerCore)Codari.getArenaManager()).buildArena(name, this.arenaBuilder);
	}
	
	//-----Random TimeLine Group-----//
	public boolean createRandomTimeLineGroup() {		
		if(this.groupName == null || this.randomTimeLineDelayTime == null) {
			return false;
		}
		if(this.randomTimeLineRepeatTime == null) {
			this.arenaBuilder.createRandomTimelineGroup(this.groupName, this.randomTimeLineDelayTime);
			this.groupName = null;
			this.resetRandomDelayTime();
		} else {
			this.arenaBuilder.createRandomTimelineGroup(this.groupName, this.randomTimeLineDelayTime, randomTimeLineRepeatTime);
			this.groupName = null;
			this.resetRandomDelayTime();
			this.resetRandomRepeatTime();
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
	
	public void resetRandomDelayTime() {
		this.randomDelayMinutes = 0;
		this.randomDelaySeconds = 0;
		this.randomDelayTicks = 0;
		this.randomTimeLineDelayTime = null;
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
	
	public void resetRandomRepeatTime() {
		this.randomRepeatMinutes = 0;
		this.randomRepeatSeconds = 0;
		this.randomRepeatTicks = 0;
		this.randomTimeLineRepeatTime = null;
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
	
	public boolean hasAllLinks(Collection<String> links) {
		return this.arenaBuilder.hasAllLinks(links);
	}
	
	public void checkIfRolesHaveRequiredLinks() {
		if(((ArenaManagerCore) Codari.getArenaManager()).hasAnExistingRole(this.name)) {
			for(String roleName : ((ArenaManagerCore) Codari.getArenaManager()).getExistingRoleNames(this.name)) {
				Role role = ((ArenaManagerCore) Codari.getArenaManager()).getExistingRole(this.name, roleName);
				if(!this.hasAllLinks(((RoleCore) role).getObjectsWithLinks())) {
					((ArenaManagerCore) Codari.getArenaManager()).clearRole(this.name, roleName);
					for(Combatant combatant : ((ArenaManagerCore) Codari.getArenaManager()).getCombatants()) {
						combatant.getPlayer().sendMessage(ChatColor.BLUE + roleName + " has been removed from " + this.name + " because "
								+ "it is missing a link with one of the added Arena Objects!");
						((CombatantCore) combatant).getDynamicMenuManager().removeArenaRoleIcon(this, roleName);
					}
				}
			}
		}
	}
	
	//-----Role Selection-----//
	public void addRoleData(RoleData roleData) {
		this.roleDatas.put(roleData.getRole().getName(), roleData);
	}
	
	public Map<String, RoleData> getRoleDatas() {
		return this.roleDatas;
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
	
	protected String addSpawn(ItemStack itemStack, Location location) {
		this.addSpawn(location, this.tools[4]);
		this.addSpawn(location, itemStack);
		return this.spawnString(location);
	}
	
	private void addSpawn(Location location, ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lore;
		if(meta.hasLore()) {
			lore = meta.getLore();
		} else {
			lore = new ArrayList<>();
		}
		lore.add(spawnString(location));
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
	}
	
	private String spawnString(Location location) {
		return ChatColor.GREEN + "Spawn #" + 
				this.arenaBuilder.getNumberOfSpawns() + ":" +
				" x=" + location.getBlockX() +
				" y=" + location.getBlockY() +
				" z=" + location.getBlockZ();
	}
}
