package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.rolesettings;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codari.api5.Codari;
import com.codari.api5.CodariI;
import com.codari.apicore.CodariCore;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.role.RoleCore;
import com.codari.arenacore.players.role.RoleManagerCore;

public class ArenaRoleIcon extends ExecutableIcon {
	private String roleName;

	public ArenaRoleIcon(Combatant combatant, String roleName) {
		super(Material.SLIME_BALL, combatant, roleName);
		this.roleName = roleName;
	}

	@Override
	public void click() {
		Kit kit = KitListener.getKit(this.getCombatant());
		String arenaName = kit.getName();
		RoleManagerCore roleManager = ((CodariCore) CodariI.INSTANCE).getRoleManager();
		if(roleManager.containsRole(this.roleName)) {
			Role role = roleManager.getRole(this.roleName);
			if(Codari.getArenaManager().getExistingRole(arenaName, this.roleName) == null) {
				if(kit.hasAllLinks(((RoleCore) role).getObjectsWithLinks())) {
					((ArenaManagerCore) Codari.getArenaManager()).submitRole(arenaName, role);
					this.getCombatant().getPlayer().sendMessage(ChatColor.GREEN + "Role added!");
					((CombatantCore) this.getCombatant()).getDynamicMenuManager().addArenaRoleIcon(kit, arenaName);
				} else {
					this.getCombatant().getPlayer().sendMessage(ChatColor.RED + "Failed to add role! - " + this.roleName + " does not have "
							+ "all the required links for the arena's objects!");
				}
			} else {
				this.getCombatant().getPlayer().sendMessage(ChatColor.BLUE + "You've already added this role to the arena!");
			}
		}
	}

}
