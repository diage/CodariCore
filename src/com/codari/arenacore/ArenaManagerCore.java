package com.codari.arenacore;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.codari.api5.Codari;
import com.codari.api5.util.PlayerReference;
import com.codari.arena5.Arena;
import com.codari.arena5.ArenaBuilder;
import com.codari.arena5.ArenaManager;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.teams.Team;
import com.codari.arena5.players.teams.TeamColor;
import com.codari.arena5.rules.GameRule;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.combatants.CombatantDataCore;

public class ArenaManagerCore implements ArenaManager {
	//-----Fields-----//
	private final Map<String, Combatant> combatants;
	private final Map<String, ArenaCore> arenas;
	private final Map<String, Class<? extends ArenaObject>> objectos;
	
	//TODO
	private Arena onlyArena = null;
	
	//-----Constructor-----//
	public ArenaManagerCore() {
		this.combatants = new HashMap<>();
		this.arenas = new HashMap<>();
		this.objectos = new HashMap<>();
		ConfigurationSerialization.registerClass(CombatantDataCore.class);
	}
	
	//-----Public Methods-----//
	@Override
	public Combatant getCombatant(String name) {
		Combatant combatant = this.combatants.get(name);
		if (combatant == null) {
			combatant = new CombatantCore(PlayerReference.instanceOf(name));
			this.combatants.put(name, combatant);
		}
		return combatant;
	}
	
	@Override
	public Combatant getCombatant(OfflinePlayer player) {
		return this.getCombatant(player.getName());
	}
	
	@Override
	public Combatant getCombatant(PlayerReference playerReference) {
		return this.getCombatant(playerReference.getName());
	}
	
	@Override
	public ArenaCore getArena(String name) {
		return this.arenas.get(name);
	}

	@Override
	public Arena buildArena(ArenaBuilder arenaBuilder, String requestedName) {
		
		return null;
	}

	@Override
	public ArenaBuilder getArenaBuider(GameRule gameRule) {
		return new ArenaBuilderCore(gameRule);
	}

	@Override
	public Team getTeam(String arenaName, String teamName) {
		return null;
	}

	@Override
	public Team getTeam(Combatant combatant) {
		if(this.onlyArena.getTeams().get(TeamColor.RED).getTeamMates(combatant).size() > 0) {
			return this.onlyArena.getTeams().get(TeamColor.RED);
		} else if (this.onlyArena.getTeams().get(TeamColor.BLUE).getTeamMates(combatant).size() > 0){
			return this.onlyArena.getTeams().get(TeamColor.BLUE);
		}
		return null;
	}
	
	public void tempBuildArena(Combatant...combatants) {
		this.onlyArena = new ArenaCore("NEVER MORE THAN ONE ARENA!!", combatants);
	}

	@Override
	public boolean createTeam(Team team) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerArenaObjecto(Class<? extends ArenaObject> clazz) {
		ArenaObjectName nemo = clazz.getAnnotation(ArenaObjectName.class);
		if (nemo == null) {
			throw new IllegalArgumentException("Arena Object does not contain a nemo");
		}
		String dory = nemo.value();
		if (objectos.containsKey(dory)) {
			throw new IllegalArgumentException("Arena Object named " + dory + " already exists");
		}
		this.objectos.put(dory, clazz);
	}

	@Override
	public ArenaObject createObjecto(String name, Location location) {
		Class<? extends ArenaObject> clazz = this.objectos.get(name);
		if (clazz == null) {
			return null;
		}
		try {
			Constructor<? extends ArenaObject> constructor = clazz.getConstructor(Location.class);
			return constructor.newInstance(location);
		} catch (NoSuchMethodException | SecurityException |
				InstantiationException | IllegalAccessException |
				IllegalArgumentException | InvocationTargetException ex) {
			Codari.INSTANCE.getLogger().log(Level.WARNING, "Could not create arena object named " + name, ex);
			return null;
		}
	}
}