package com.codari.arenacore.players.combatants;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import com.codari.api5.Codari;
import com.codari.api5.io.CodariSerialization;
import com.codari.api5.io.CodariSerializationException;
import com.codari.api5.stats.StatManager;
import com.codari.api5.util.PlayerReference;
import com.codari.arena5.Arena;
import com.codari.arena5.events.RoleSelectEvent;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.combatants.CombatantStats;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.role.PlayerRole;
import com.codari.arenacore.players.teams.TeamCore;

public final class CombatantCore implements Combatant {
	//-----Constants-----//
	private final static String DATA_FILE_PATH = "Combatants" + File.separator + "%s" + ".dat";
	
	//-----Fields-----//
	private final PlayerReference playerReference;
	private final File dataFile;
	private CombatantDataCore data;
	private StatManager statManager;
	
	private TeamCore team;
	private Role role;
	private String arenaName;
	private boolean isLeader;
	
	//-----Constructor-----//
	public CombatantCore(PlayerReference playerReference) {
		this.playerReference = playerReference;
		String dataFilePath = String.format(DATA_FILE_PATH, this.playerReference.getName());
		this.dataFile = new File(Codari.INSTANCE.getDataFolder(), dataFilePath);
		this.reloadData();
		this.statManager = Codari.INSTANCE.getStatFactory().createStatManager(this);
		this.role = new PlayerRole(Codari.INSTANCE.getArenaManager().getExistingRole(null, "Non Combatant"));
	}
	
	//-----Public Methods-----//
	@Override
	public PlayerReference getPlayerReference() {
		return this.playerReference;
	}
	
	@Override
	public CombatantStats getStats() {
		return this.data;
	}
	
	public void reloadData() {
		try {
			this.data = (CombatantDataCore) CodariSerialization.deserialize(this.dataFile);
		} catch (CodariSerializationException ex) {
			Codari.INSTANCE.getLogger().log(Level.WARNING, 
					"Failed to load combatant data for " + this.playerReference, ex);
			if (this.data == null) {
				this.data = new CombatantDataCore();
			}
		}
	}
	
	public void saveData() {
		try {
			CodariSerialization.serialize(this.data, this.dataFile);
		} catch (CodariSerializationException ex) {
			Codari.INSTANCE.getLogger().log(Level.WARNING, 
					"Failed to save combatant data for " + this.playerReference, ex);
		}
	}

	@Override
	public Role getRole() {
		return ((PlayerRole)this.role).getInteriorRole();
	}

	@Override
	public String getArenaName() {
		return this.arenaName;
	}

	@Override
	public boolean leaveArena() {
		Arena arena = Codari.INSTANCE.getArenaManager().getArena(arenaName);
		if(arena != null) {
			//TODO - take combatant out of arena
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
			//TODO - send combatant to arena -- This method needs to initialize everything. 
			this.arenaName = arena.getName();
			return true;
		}
		return false;
	}

	@Override
	public void setRole(Role role) {
		this.role = new PlayerRole(role);
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
}