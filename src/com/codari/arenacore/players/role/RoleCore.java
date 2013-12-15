package com.codari.arenacore.players.role;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;

public class RoleCore implements Role {
	private Map<SkillActivation, Skill> skills;
	private String name;

	public RoleCore(String name) {
		this.name = name;
		this.skills = new HashMap<>();
	}

	@Override
	public Collection<Skill> getSkills() {
		return this.skills.values();
	}

	@Override
	public boolean doubleJump(Combatant combatant) {
		if(this.skills.containsKey(SkillActivation.DOUBLE_JUMP)) {
			this.skills.get(SkillActivation.DOUBLE_JUMP).activateSkill(combatant);
			return true;
		}
		return false;
	}

	@Override
	public boolean block(Combatant combatant) {
		if(this.skills.containsKey(SkillActivation.BLOCK)) {
			this.skills.get(SkillActivation.BLOCK).activateSkill(combatant);
			return true;
		}
		return false;
	}

	@Override
	public boolean sprint(Combatant combatant) {
		if(this.skills.containsKey(SkillActivation.SPRINT)) {
			this.skills.get(SkillActivation.SPRINT).activateSkill(combatant);
			return true;
		}
		return false;
	}

	@Override
	public boolean sneak(Combatant combatant) {
		if(this.skills.containsKey(SkillActivation.SNEAK)) {
			this.skills.get(SkillActivation.SNEAK).activateSkill(combatant);
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Role addSkill(Skill skill) {
		this.skills.put(skill.getSkillActivation(), skill);
		return this;
	}

	@Override
	public Role swapRole(Role role) {
		return null;
	}
}
