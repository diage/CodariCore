package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.arena.objects.RoleData;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.events.IconHoverUpdateEvent;
import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.rolesettings.AddRoleIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.ArenaObjectRandomIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.SelectNameIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.delayset.UpdateRandomDelayTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.create.repeatset.UpdateRandomRepeatTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.UpdateFixedDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.UpdateFixedDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.delayset.UpdateFixedDelayTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.UpdateFixedRepeatMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.UpdateFixedRepeatSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.fixed.repeatset.UpdateFixedRepeatTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.OverrideIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.UpdatePersistentDelayMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.UpdatePersistentDelaySecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.persistent.delayset.UpdatePersistentDelayTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.role.data.SetNumberOfRolesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.newkitcreation.SelectKitNameIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.selectionmenu.KitIcon;

public class KitListener implements Listener {
	public static Map<String, String> requestedKitNames = new HashMap<>();
	public static Map<String, String> currentRandomArenaObjectNames = new HashMap<>();
	public static Map<String, String> requestedSpawnableGroupNames = new HashMap<>();
	
	private static Map<String, Kit> currentKits = new HashMap<>();
	
	public static void changeKit(Combatant combatant, Kit kit) {
		currentKits.put(combatant.getPlayerReference().getName(), kit);
	}
	
	public static Kit getKit(Combatant combatant) {
		return currentKits.get(combatant.getPlayerReference().getName());
	}
	
	@EventHandler()
	private void kitSelection(IconMenuClickEvent e) {
		if(e.getIcon() instanceof KitIcon) {
			CombatantCore combatant = (CombatantCore)e.getIcon().getCombatant();
			String displayName = ((KitIcon)e.getIcon()).getDisplayName();
			if(combatant != null && displayName != null) {
				changeKit(combatant, combatant.getKitManager().getKit(displayName)); 
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + "Something is wrong in KitListener!");	//TODO
			}
		}
	}
	
	@EventHandler()
	public void setCurrentRandomArenaObjectName(IconMenuClickEvent e) {
		if(e.getIcon() instanceof ArenaObjectRandomIcon) {
			currentRandomArenaObjectNames.put(e.getIcon().getPlayerName(), ((ArenaObjectRandomIcon)e.getIcon()).getRandomArenaObjectName());
		}
	}	
	
	@EventHandler()
	public void setKitName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectKitNameIcon) {
			requestedKitNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());			
		}
	}	

	@EventHandler()
	public void setSpawnableGroupName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectNameIcon) {
			requestedSpawnableGroupNames.put(e.getIcon().getPlayerName(), e.getPlayerInput());			
		}
	}		
	
	@EventHandler()
	private void changeRandomDelayTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateRandomDelayMinutesIcon) {
				kit.updateRandomDelayMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateRandomDelaySecondsIcon) {
				kit.updateRandomDelaySeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateRandomDelayTicksIcon) {
				kit.updateRandomDelayTicks(e.getNewInput());
			}
		}
	}
	
	@EventHandler()
	private void changeRandomRepeatTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateRandomRepeatMinutesIcon) {
				kit.updateRandomRepeatMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateRandomRepeatSecondsIcon) {
				kit.updateRandomRepeatSeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateRandomRepeatTicksIcon) {
				kit.updateRandomRepeatTicks(e.getNewInput());
			}
		}		
	}
	
	@EventHandler()
	private void changeFixedDelayTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateFixedDelayMinutesIcon) {
				kit.updateFixedDelayMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateFixedDelaySecondsIcon) {
				kit.updateFixedDelaySeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateFixedDelayTicksIcon) {
				kit.updateFixedDelayTicks(e.getNewInput());
			}
		}		
	}
	
	@EventHandler()
	private void changeFixedRepeatTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdateFixedRepeatMinutesIcon) {
				kit.updateFixedRepeatMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateFixedRepeatSecondsIcon) {
				kit.updateFixedRepeatSeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdateFixedRepeatTicksIcon) {
				kit.updateFixedRepeatTicks(e.getNewInput());
			}
		}		
	}
	
	@EventHandler()
	private void changePersistentDelayTime(IconHoverUpdateEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(!(kit == null)) {
			if(e.getIcon() instanceof UpdatePersistentDelayMinutesIcon) {
				kit.updateFixedDelayMinutes(e.getNewInput());
			} else if(e.getIcon() instanceof UpdatePersistentDelaySecondsIcon) {
				kit.updateFixedDelaySeconds(e.getNewInput());
			} else if(e.getIcon() instanceof UpdatePersistentDelayTicksIcon) {
				kit.updateFixedDelayTicks(e.getNewInput());
			}
		}		
	}
	
	@EventHandler()
	private void changePersistentBoolean(IconRequestEvent e) {
		Kit kit = KitListener.currentKits.get(e.getIcon().getCombatant().getPlayerReference().getName());
		if(e.getIcon() instanceof OverrideIcon) {
			kit.setOverride(Boolean.parseBoolean(e.getPlayerInput()));
		}
	}	
	
	@EventHandler()
	private void setNumberOfRoles(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetNumberOfRolesIcon) {
			Kit kit = getKit(e.getIcon().getCombatant());
			if(kit != null) {
				String roleName = ((SetNumberOfRolesIcon) e.getIcon()).getRoleName();
				Role role = ((ArenaManagerCore) Codari.getArenaManager()).getExistingRole(kit.getName(), roleName);
				int numberOfRoles = e.getNewInput();
				kit.addRoleData(new RoleData(role, numberOfRoles));
				e.getIcon().getCombatant().getPlayer().sendMessage(ChatColor.AQUA + roleName + " set to " + numberOfRoles + ".");
			}
		}
	}
	
	@EventHandler()
	private void setBackIcon(IconMenuClickEvent e) {
		if(e.getIcon() instanceof AddRoleIcon) {
			CombatantCore combatant = ((CombatantCore) e.getIcon().getCombatant());
			combatant.getDynamicMenuManager().setRoleAdditionMenu(new BackIcon(combatant, ((AddRoleIcon) e.getIcon()).getRoleSettings()));
		}
	}
}
