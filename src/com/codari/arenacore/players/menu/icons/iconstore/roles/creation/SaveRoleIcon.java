package com.codari.arenacore.players.menu.icons.iconstore.roles.creation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.RoleMenuListener;
import com.codari.arenacore.players.role.RoleBuilder;

public class SaveRoleIcon extends ExecutableIcon {

	public SaveRoleIcon(Combatant combatant) {
		super(Material.STONE_HOE, combatant, "Save Role");
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(RoleMenuListener.currentRoleFactories.containsKey(player.getName())) {
			RoleBuilder roleFactory = RoleMenuListener.currentRoleFactories.get(player.getName());
			if(roleFactory.getName() != null) {
				String roleName = roleFactory.getName();
				if(roleFactory.buildRole()) {
					player.sendMessage(ChatColor.GREEN + "Successfully built the role named " + roleName + ".");
					for(Combatant combatant : ((ArenaManagerCore)Codari.getArenaManager()).getCombatants()) {
						((CombatantCore) combatant).getDynamicMenuManager().addRoleIcon(roleName);				
					}
					RoleMenuListener.currentRoleFactories.remove(player.getName());
				} else {
					player.sendMessage(ChatColor.RED + "There is already a role named " + roleName + ". Please choose another name.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You must set a name for the role!");
			}
		}
	}

}
