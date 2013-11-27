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
	public void doubleJump(Combatant combatant) {
		this.skills.get(SkillActivation.DOUBLE_JUMP).activateSkill(combatant);
	}

	@Override
	public void block(Combatant combatant) {
		this.skills.get(SkillActivation.BLOCK).activateSkill(combatant);
	}

	@Override
	public void sprint(Combatant combatant) {
		this.skills.get(SkillActivation.SPRINT).activateSkill(combatant);
	}

	@Override
	public void sneak(Combatant combatant) {
		this.skills.get(SkillActivation.SNEAK).activateSkill(combatant);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void addSkill(Skill skill) {
		this.skills.put(skill.getSkillActivation(), skill);
	}

	@Override
	public Role swapRole(Role role) {
		return null;
	}
}
