package com.codari.arenacore.players.role;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.codari.api5.CodariI;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.skills.Skill;

public class PlayerRole implements Role {
	private final int DEFAULT_MAX_COOLDOWN = 20;
	private final int MAX_COOLDOWN;
	private int cooldown;
	private Role role;
	private Player player;

	public PlayerRole() {
		this.role = null;
		this.MAX_COOLDOWN = this.DEFAULT_MAX_COOLDOWN;
	}

	public PlayerRole(Combatant combatant, Role role) {
		if(role instanceof PlayerRole) {
			this.role = ((PlayerRole) role).getInteriorRole();
		} else {
			this.role = role;
		}
		this.MAX_COOLDOWN = this.DEFAULT_MAX_COOLDOWN;
		this.player = combatant.getPlayer();
	}

	public PlayerRole(Role role, int cooldown) {
		if(role instanceof PlayerRole) {
			this.role = ((PlayerRole) role).getInteriorRole();
		} else {
			this.role = role;
		}
		this.MAX_COOLDOWN = cooldown;
	}

	@Override
	public Collection<Skill> getSkills() {
		if(this.role == null) {
			return null;
		}
		return this.role.getSkills();
	}

	@Override
	public boolean doubleJump(Combatant combatant) {
		if(this.role != null) {
			Bukkit.broadcastMessage("You did a double jump!!");
			if(this.cooldown == 0) {
				this.startCooldown();
				return this.role.doubleJump(combatant);
			}
		}
		return false;
	}
	
	@Override
	public boolean skill(Combatant combatant) {
		if(this.role != null) {
			if(this.cooldown == 0) {
				this.startCooldown();
				return this.role.skill(combatant);
			}
		}
		return false;
	}

	@Override
	public String getName() {
		if(this.role == null) {
			return null;
		}
		return this.role.getName();
	}

	@Override
	public Role addSkill(Skill skill) {
		if(this.role == null) {
			return null;
		}
		return this.role.addSkill(skill);
	}

	@Override
	public Role swapRole(Role role) {
		Role tempRole = this.role;
		if(role instanceof PlayerRole) {
			this.role = ((PlayerRole) role).getInteriorRole();
		} else {
			this.role = role;
		}
		return tempRole;
	}

	public Role getInteriorRole() {
		return this.role;
	}

	public void setInteriorRole(Role role) {
		if(role instanceof PlayerRole) {
			this.role = ((PlayerRole) role).getInteriorRole();
		} else {
			this.role = role;
		}
	}

	private void startCooldown() {
		this.cooldown = this.MAX_COOLDOWN;

		BukkitRunnable runner = new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if(cooldown > 0) {
					player.getInventory().getItem(0).setAmount(cooldown);
					player.updateInventory();
					cooldown--;
				} else {
					super.cancel();
				}
			}
		};

		runner.runTaskTimer(CodariI.INSTANCE, 20, 20);
	}
}
