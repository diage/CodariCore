package com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.selection.role.data;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arenacore.arena.ArenaManagerCore;
import com.codari.arenacore.arena.objects.RoleData;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.builders.kit.KitListener;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;

public class SetInfiniteRolesIcon extends ExecutableIcon {
	private String roleName;
	
	public SetInfiniteRolesIcon(Combatant combatant, String roleName) {
		super(Material.STONE_PICKAXE, combatant, "Set Infinite Number of Roles");
		this.roleName = roleName;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		Kit kit = KitListener.getKit(this.getCombatant());
		if(kit != null) {
			Role role = ((ArenaManagerCore) Codari.getArenaManager()).getExistingRole(kit.getName(), this.roleName);
			kit.addRoleData(new RoleData(role, RoleData.INFINITE));
			player.sendMessage(ChatColor.AQUA + this.roleName + " set to infinite.");
		}
	}

}
