package com.codari.arenacore.players.role;

import java.util.Collection;

import org.bukkit.scheduler.BukkitRunnable;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.skills.Skill;

public class PlayerRole implements Role {
	private final int DEFAULT_MAX_COOLDOWN = 20;
	private final int MAX_COOLDOWN;
	private int cooldown;
	private Role role;
	
	public PlayerRole() {
		this.role = null;
		this.MAX_COOLDOWN = this.DEFAULT_MAX_COOLDOWN;
	}
	
	public PlayerRole(Role role) {
		if(role instanceof PlayerRole) {
			this.role = ((PlayerRole) role).getInteriorRole();
		} else {
			this.role = role;
		}
		this.MAX_COOLDOWN = this.DEFAULT_MAX_COOLDOWN;
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
	public void doubleJump(Combatant combatant) {
		if(this.role == null) {
			return;
		}
		if(this.cooldown == 0) {
			this.role.doubleJump(combatant);
			this.startCooldown();
		}
	}

	@Override
	public void block(Combatant combatant) {
		if(this.role == null) {
			return;
		}
		if(this.cooldown == 0) {
			this.role.block(combatant);
			this.startCooldown();
		}
	}

	@Override
	public void sprint(Combatant combatant) {
		if(this.role == null) {
			return;
		}
		if(this.cooldown == 0) {
			this.role.sprint(combatant);
			this.startCooldown();
		}
	}

	@Override
	public void sneak(Combatant combatant) {
		if(this.role == null) {
			return;
		}
		if(this.cooldown == 0) {
			this.role.sneak(combatant);
			this.startCooldown();
		}
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
			@Override
			public void run() {
				if(cooldown != 0) {
					cooldown--;
				} else {
					super.cancel();
				}
			}
		};
		
		runner.runTaskTimer(Codari.INSTANCE, 20, 20);
	}
}
