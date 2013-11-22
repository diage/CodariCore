package com.codari.arenacore.players.combatants;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import com.codari.api5.Codari;
import com.codari.api5.io.CodariIO;
import com.codari.api5.stats.StatManager;
import com.codari.api5.util.PlayerReference;
import com.codari.arena5.Arena;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.combatants.CombatantStats;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.players.teams.TeamBuilder;
import com.codari.arenacore.players.teams.TeamCore;

public final class CombatantCore implements Combatant {
	//-----Constants-----//
	private final static String DATA_FILE_PATH = "Combatants" + File.separator + "%s" + ".dat";
	
	//-----Fields-----//
	private final PlayerReference playerReference;
	private final File dataFile;
	private CombatantDataCore data;
	private StatManager statManager;
	
	//-----Constructor-----//
	public CombatantCore(PlayerReference playerReference) {
		this.playerReference = playerReference;
		String dataFilePath = String.format(DATA_FILE_PATH, this.playerReference.getName());
		this.dataFile = new File(Codari.INSTANCE.getDataFolder(), dataFilePath);
		this.reloadData();
		this.statManager = Codari.INSTANCE.getStatFactory().createStatManager(this);
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
			this.data = (CombatantDataCore) CodariIO.deserialize(this.dataFile);
		} catch (ClassNotFoundException | IOException ex) {
			Codari.INSTANCE.getLogger().log(Level.WARNING, 
					"Failed to load combatant data for " + this.playerReference, ex);
			if (this.data == null) {
				this.data = new CombatantDataCore();
			}
		}
	}
	
	public void saveData() {
		try {
			CodariIO.serialize(this.data, this.dataFile);
		} catch (IOException ex) {
			Codari.INSTANCE.getLogger().log(Level.WARNING, 
					"Failed to save combatant data for " + this.playerReference, ex);
		}
	}

	@Override
	public Role getRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArenaName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean leaveArena() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public StatManager getStatManager() {
		return this.statManager;
	}

	@Override
	public Team getTeam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendToArena(Arena arena) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRole(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTeam(Team team) {
		TeamBuilder.invitePlayer((TeamCore) team, this.playerReference.getPlayer());
		
	}
}