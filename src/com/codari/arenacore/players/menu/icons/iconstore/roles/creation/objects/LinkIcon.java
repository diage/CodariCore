package com.codari.arenacore.players.menu.icons.iconstore.roles.creation.objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.RoleMenuListener;
import com.codari.arenacore.players.role.RoleBuilder;

public class LinkIcon extends ExecutableIcon {
	private String arenaObjectName;
	private String linkName;
	
	public LinkIcon(Combatant combatant, String arenaObjectName, String linkName) {
		super(Material.STAINED_CLAY, combatant, linkName);
		this.arenaObjectName = arenaObjectName;
		this.linkName = linkName;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(!RoleMenuListener.currentRoleFactories.containsKey(player.getName())) {
			RoleMenuListener.currentRoleFactories.put(player.getName(), new RoleBuilder());
		}
		RoleMenuListener.currentRoleFactories.get(player.getName()).addLink(this.arenaObjectName, this.linkName);
		player.sendMessage(ChatColor.GREEN + this.linkName + " link has been set for " + this.arenaObjectName);
	}

}
