package com.codari.arenacore.players.combatants;

import java.io.File;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.codari.api5.CodariI;
import com.codari.api5.player.CodariPlayers;
import com.codari.api5.stats.StatManager;
import com.codari.api5.util.scheduler.CodariRunnable;
import com.codari.apicore.player.OfflineCodariPlayerCore;
import com.codari.arena5.Arena;
import com.codari.arena5.events.RoleSelectEvent;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.combatants.CombatantStats;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.menu.MenuManager;
import com.codari.arenacore.players.role.PlayerRole;
import com.codari.arenacore.players.teams.TeamCore;

public final class CombatantCore implements Combatant {
	//-----Constants-----//
	private final static String DATA_FILE_PATH = "Combatants" + File.separator + "%s" + ".dat";
	
	//-----Fields-----//
	private final OfflineCodariPlayerCore player;
	@SuppressWarnings("unused")
	private final File dataFile;
	private CombatantDataCore data;
	private StatManager statManager;
	private MenuManager menuManager;
	
	private boolean isLeader;
	private TeamCore team;
	private Role role;
	private String arenaName;
	
	//---Hotbar---//
	private boolean activeHotbar;
	private final CodariRunnable hotbarCooldown;
	
	//---Building Arena---//
	private boolean isBuilding;
	private String currentArenaBuildName;
	
	//-----Constructor-----//
	public CombatantCore(String name) {
		this.player = (OfflineCodariPlayerCore) CodariPlayers.getOfflineCodariPlayer(name);
		String dataFilePath = String.format(DATA_FILE_PATH, this.player.getName());
		this.dataFile = new File(CodariI.INSTANCE.getDataFolder(), dataFilePath);
		
		this.activeHotbar = false;
		this.hotbarCooldown = new CodariRunnable(CodariI.INSTANCE) {public void run() {}};
		
		this.statManager = CodariI.INSTANCE.getStatFactory().createStatManager(this);
		this.role = new PlayerRole(this, CodariI.INSTANCE.getArenaManager().getExistingRole(null, "Non Combatant"));
		//this.menuManager = new MenuManager(this);
	}
	
	//-----Public Methods-----//
	@Override
	public OfflineCodariPlayerCore getPlayerReference() {
		return this.player;
	}
	
	@Override
	public CombatantStats getStats() {
		return this.data;
	}
	
	public void reloadData() {
		/*try {
			this.data = (CombatantDataCore) CodariSerialization.deserialize(this.dataFile);
		} catch (CodariSerializationException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, 
					"Failed to load combatant data for " + this.playerReference, ex);
			if (this.data == null) {
				this.data = new CombatantDataCore();
			}
		}*/
	}
	
	public void saveData() {
		/*try {
			CodariSerialization.serialize(this.data, this.dataFile);
		} catch (CodariSerializationException ex) {
			CodariI.INSTANCE.getLogger().log(Level.WARNING, 
					"Failed to save combatant data for " + this.playerReference, ex);
		}*/
	}

	@Override
	public Role getRole() {
		return ((PlayerRole)this.role).getInteriorRole();
	}
	
	public void doubleJump(Combatant combatant) {
		this.role.doubleJump(combatant);
	}

	public void block(Combatant combatant) {
		this.role.block(combatant);
	}

	public void sprint(Combatant combatant) {
		this.role.sprint(combatant);
	}

	public void sneak(Combatant combatant) {
		this.role.sneak(combatant);
	}

	@Override
	public String getArenaName() {
		return this.arenaName;
	}

	@Override
	public boolean leaveArena() {
		Arena arena = CodariI.INSTANCE.getArenaManager().getArena(arenaName);
		if(arena != null) {
			this.arenaName = null;
			return true;
		}
		return false;
	}
	
	@Override
	public StatManager getStatManager() {
		return this.statManager;
	}

	@Override
	public Team getTeam() {
		return this.team;
	}

	@Override
	public boolean sendToArena(Arena arena) {
		if(arena != null) {
			this.arenaName = arena.getName();
			return true;
		}
		return false;
	}

	@Override
	public void setRole(Role role) {
		this.role = new PlayerRole(this, role);
		if(role instanceof PlayerRole) {
			Bukkit.getPluginManager().callEvent(new RoleSelectEvent(((PlayerRole)role).getInteriorRole(), this));
		} else {
			Bukkit.getPluginManager().callEvent(new RoleSelectEvent(role, this));
		}
	}

	@Override
	public void setTeam(Team team) {
		this.team = (TeamCore) team;
	}
	
	@Override
	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}
	
	@Override
	public boolean checkIfLeader() {
		return this.isLeader;
	}

	@Override
	public Player getPlayer() {
		return this.player.getPlayer();
	}

	@Override
	public Role swapRole(Role role) {
		if(role instanceof PlayerRole) {
			Bukkit.getPluginManager().callEvent(new RoleSelectEvent(((PlayerRole)role).getInteriorRole(), ((PlayerRole)this.role).getInteriorRole(), this));
		} else {
			Bukkit.getPluginManager().callEvent(new RoleSelectEvent(role, ((PlayerRole)this.role).getInteriorRole(), this));
		}
		return this.role.swapRole(role);
	}
	
	public void startBuilding(String arenaName) {
		this.currentArenaBuildName = arenaName;
		this.isBuilding = true;
	}
	
	public void endBuilding() {
		this.currentArenaBuildName = null;
		this.isBuilding = false;
	}
	
	public boolean checkIfBuilding() {
		return this.isBuilding;
	} 
	
	public String getArenaBuildName() {
		return this.currentArenaBuildName;
	}
	
	public MenuManager getMenuManager() {
		return this.menuManager;
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(this.player).toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof CombatantCore) {
			return this.player.equals(((CombatantCore) obj).player);
		}
		return false;
	}

	@Override
	public boolean isHotbarActive() {
		return this.activeHotbar;
	}

	@Override
	public void setHotbarActibe(boolean active) {
		this.activeHotbar = active;
	}
	
	@Override
	public boolean isHotbarOnCooldown() {
		return this.hotbarCooldown.isRunning();
	}

	@Override
	public void setHotbarCooldown(long ticks) {
		if (ticks <= 0) {
			this.hotbarCooldown.cancel();
		}
		this.hotbarCooldown.runTaskLater(ticks, true);
	}
}