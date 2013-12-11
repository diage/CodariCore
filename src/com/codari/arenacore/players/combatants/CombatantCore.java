package com.codari.arenacore.players.combatants;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.stats.StatManager;
import com.codari.api5.util.PlayerReference;
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
	private final PlayerReference playerReference;
	@SuppressWarnings("unused")	//TODO
	private final File dataFile;
	private CombatantDataCore data;
	private StatManager statManager;
	private MenuManager menuManager;
	
	private boolean isLeader;
	private TeamCore team;
	private Role role;
	private String arenaName;
	
	//---Building Arena---//
	private boolean isBuilding;
	private String currentArenaBuildName;
	
	//-----Constructor-----//
	public CombatantCore(PlayerReference playerReference) {
		this.playerReference = playerReference;
		String dataFilePath = String.format(DATA_FILE_PATH, this.playerReference.getName());
		this.dataFile = new File(CodariI.INSTANCE.getDataFolder(), dataFilePath);
		this.reloadData();
		this.statManager = CodariI.INSTANCE.getStatFactory().createStatManager(this);
		this.role = new PlayerRole(this, Codari.getArenaManager().getExistingRole(null, "Non Combatant"));
		this.menuManager = new MenuManager(this);
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
		return this.playerReference.getPlayer();
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
	
	public boolean equals(Object obj) {
		if(obj instanceof CombatantCore) {
			CombatantCore combatant = (CombatantCore) obj;
			return (combatant.getPlayer().getName().equalsIgnoreCase(this.getPlayer().getName()));
		}
		return false;
	}
}