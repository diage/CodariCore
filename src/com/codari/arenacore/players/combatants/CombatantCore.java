package com.codari.arenacore.players.combatants;

import java.io.File;
import java.util.UUID;

import net.minecraft.util.org.apache.commons.lang3.builder.HashCodeBuilder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.api5.events.RoleSelectEvent;
import com.codari.api5.player.CodariPlayer;
import com.codari.api5.util.scheduler.BukkitTime;
import com.codari.api5.util.scheduler.CodariRunnable;
import com.codari.apicore.CodariCore;
import com.codari.apicore.item.SpellAttributeLinker;
import com.codari.arena5.arena.Arena;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.combatants.CombatantStats;
import com.codari.arena5.players.guilds.Guild;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.teams.Team;
import com.codari.arenacore.arena.ArenaCore;
import com.codari.arenacore.players.builders.ToolbarManager;
import com.codari.arenacore.players.builders.kit.KitManager;
import com.codari.arenacore.players.guilds.GuildCore;
import com.codari.arenacore.players.menu.DynamicMenuManager;
import com.codari.arenacore.players.menu.MenuManager;
import com.codari.arenacore.players.role.PlayerRole;
import com.codari.arenacore.players.role.RoleCore;
import com.codari.arenacore.players.teams.TeamCore;

public final class CombatantCore implements Combatant {
	//-----Constants-----//
	public final static String NON_COMBATANT = "Non Combatant";
	private final static String DATA_FILE_PATH = "Combatants" + File.separator + "%s" + ".dat";
	private final static Role NON_COMBATANT_ROLE = new RoleCore(NON_COMBATANT, null);
	
	//-----Fields-----//
	private final CodariPlayer player;
	@SuppressWarnings("unused")
	private final File dataFile;
	private CombatantDataCore data;
	private DynamicMenuManager dynamicMenuManager;
	private MenuManager menuManager;
	private SpellAttributeLinker spellAttributeLinker;
	
	private boolean isLeader, inArena;
	private TeamCore team, inviteTeam;
	private GuildCore guild, inviteGuild;
	private Role role;
	private String arenaName;
	
	//---Hotbar---//
	//***Skill Bar***//
	private boolean activeHotbar;
	private final CodariRunnable hotbarCooldown;
	
	//***Arena Tool Bar***//
	private ToolbarManager toolbarManager;
	
	//---Building Arena---//
	private boolean isBuilding;
	private String currentArenaBuildName;
	
	//-----Constructor-----//
	public CombatantCore(UUID uuid) {
		this.player = CodariCore.instance().getCodariPlayerManager().getCodariPlayer(uuid);
		String dataFilePath = String.format(DATA_FILE_PATH, this.player.getName());
		this.dataFile = new File(CodariI.INSTANCE.getDataFolder(), dataFilePath);	
		this.activeHotbar = false;
		this.hotbarCooldown = new CodariRunnable(CodariI.INSTANCE) {public void run() {}};	
		this.role = new PlayerRole(this, NON_COMBATANT_ROLE);		
		this.dynamicMenuManager = new DynamicMenuManager(this);	
		this.menuManager = new MenuManager(this); 
		this.toolbarManager = new ToolbarManager(this);
		this.inArena = false;
		this.spellAttributeLinker = new SpellAttributeLinker();
	}
	
	//-----Public Methods-----//
	@Override
	public CodariPlayer getPlayerReference() {
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
	
	public void doubleJump() {
		this.role.doubleJump(this);
	}
	
	@Override
	public void skill() {
		this.role.skill(this);
	}

	@Override
	public String getArenaName() {
		return this.arenaName;
	}

	@Override
	public boolean leaveArena() {
		Arena arena = Codari.getArenaManager().getArena(this.arenaName);
		if(arena != null) {
			this.getPlayer().sendMessage(ChatColor.DARK_BLUE + "You are leaving the arena: " + this.arenaName);
			this.arenaName = null;
			this.inArena = false;
			this.setRole(NON_COMBATANT_ROLE);
			return true;
		}
		return false;
	}

	@Override
	public Team getTeam() {
		return this.team;
	}

	@Override
	public boolean sendToArena(Arena arena) {
		if(arena != null) {
			this.getPlayer().sendMessage(ChatColor.AQUA + "You are being sent to the arena: " + arena.getName());
			this.arenaName = arena.getName();
			this.inArena = true;
			this.setHotbarCooldown(BukkitTime.SECOND.tickValueOf(1));
			this.setHotbarActive(true);
			this.getPlayer().teleport(((ArenaCore)arena).getSpawn(this));
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
	
	public void setBeingInvitedToTeam(Team inviteTeam) {
		this.inviteTeam = (TeamCore) team;
	}
	
	public boolean checkIfBeingInvitedToTeam() {
		return this.inviteTeam != null;
	}
	
	public Team getInviteTeam() {
		return this.inviteTeam;
	}
	
	public void setBeingInvitedToGuild(Guild inviteGuild) {
		this.inviteGuild = (GuildCore) inviteGuild;
	}
	
	public boolean checkIfBeingInvitedToGuild() {
		return this.inviteGuild != null;
	}
	
	public Guild getInviteGuild() {
		return this.inviteGuild;
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
	
	public DynamicMenuManager getDynamicMenuManager() {
		return this.dynamicMenuManager;
	}
	
	public MenuManager getMenuManager() {
		return this.menuManager;
	}
	
	public KitManager getKitManager() {
		return ((CodariCore) CodariI.INSTANCE).getKitManager();
	}
	
	public ToolbarManager getToolbarManager() {
		return this.toolbarManager;
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
	public void setHotbarActive(boolean active) {
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

	@Override
	public boolean inArena() {
		return this.inArena;
	}
	
	public void setGuild(GuildCore guild) {
		this.guild = guild;
	}
	
	public GuildCore getGuild() {
		return this.guild;
	}
	
	public SpellAttributeLinker getSpellAttributeLinker() {
		return this.spellAttributeLinker;
	}
}
