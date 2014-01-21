package com.codari.arenacore.players.menu.icons.iconstore.roles.creation.skills;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.skills.Skill;
import com.codari.arenacore.players.menu.icons.ExecutableIcon;
import com.codari.arenacore.players.menu.icons.iconstore.listeners.RoleMenuListener;
import com.codari.arenacore.players.role.RoleFactory;

public class SkillIcon extends ExecutableIcon {
	private Skill skill;
	
	public SkillIcon(Combatant combatant, Skill skill, String skillName) {
		super(Material.COCOA, combatant, skillName);
		this.skill = skill;
	}

	@Override
	public void click() {
		Player player = this.getCombatant().getPlayer();
		if(!RoleMenuListener.currentRoleFactories.containsKey(player.getName())) {
			RoleMenuListener.currentRoleFactories.put(player.getName(), new RoleFactory());
		}
		RoleMenuListener.currentRoleFactories.get(player.getName()).addSkill(this.skill);
		player.sendMessage(ChatColor.GREEN + "Skill added!");
	}

}
