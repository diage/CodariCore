package com.codari.arenacore.players.combatants;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import com.codari.api.Codari;
import com.codari.api.io.CodariIO;
import com.codari.api.util.PlayerReference;
import com.codari.arena.Arena;
import com.codari.arena.players.combatants.Combatant;
import com.codari.arena.players.combatants.CombatantStats;
import com.codari.arena.players.role.Role;

public final class CombatantCore implements Combatant {
	//-----Constants-----//
	private final static String DATA_FILE_PATH = "Combatants" + File.separator + "%s" + ".dat";
	
	//-----Fields-----//
	private final PlayerReference playerReference;
	private final File dataFile;
	private CombatantDataCore data;
	
	//-----Constructor-----//
	public CombatantCore(PlayerReference playerReference) {
		this.playerReference = playerReference;
		String dataFilePath = String.format(DATA_FILE_PATH, this.playerReference.getName());
		this.dataFile = new File(Codari.INSTANCE.getDataFolder(), dataFilePath);
		this.reloadData();
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
	public boolean sendToArena(Arena arena, Role role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean leaveArena() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Role swapRole(Role role) {
		// TODO Auto-generated method stub
		return null;
	}
}