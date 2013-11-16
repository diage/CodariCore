package com.codari.arenacore.players.role;

import java.util.HashMap;
import java.util.Map;

import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.role.RoleType;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;

public class RoleCore implements Role {
	//-----Fields-----//
	private Map<SkillActivation, Skill> skills;
	
	public RoleCore(Skill...skills) {
		this.skills = new HashMap<>();
		for(Skill skill : skills) {
			this.skills.put(skill.getSkillActivation(), skill);
		}
	}
	
	@Override
	public Skill[] getSkills() {
		return (Skill[]) this.skills.values().toArray();
	}

	@Override
	public void doubleJump() {
		if(this.skills.containsKey(SkillActivation.DOUBLE_JUMP)) {
			this.skills.get(SkillActivation.DOUBLE_JUMP).activateSkill(); 
		}		
	}

	@Override
	public void block() {
		if(this.skills.containsKey(SkillActivation.BLOCK)) {
			this.skills.get(SkillActivation.BLOCK).activateSkill(); 
		}
	}

	@Override
	public void sprint() {
		if(this.skills.containsKey(SkillActivation.SPRINT)) {
			this.skills.get(SkillActivation.SPRINT).activateSkill(); 
		}	
	}

	@Override
	public void sneak() {
		if(this.skills.containsKey(SkillActivation.SNEAK)) {
			this.skills.get(SkillActivation.SNEAK).activateSkill(); 
		}
	}

	@Override
	public RoleType getRoleType() {
		// TODO Auto-generated method stub
		return null;
	}

}
