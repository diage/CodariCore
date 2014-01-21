package com.codari.arenacore.players.role;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.codari.api5.CodariI;
import com.codari.apicore.CodariCore;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;

public class RoleFactory {
	private String roleName;
	private Map<SkillActivation, Skill> skills;
	private Map<String, String> links;
	
	public RoleFactory() {
		this.skills = new HashMap<>();
		this.links = new HashMap<>();
	}
	
	public void setName(String roleName) {
		this.roleName = roleName;
	}
	public String getName() {
		return this.roleName;
	}
	
	public void addSkill(Skill skill) {
		this.skills.put(skill.getSkillActivation(), skill);
	}
	
	public void addLink(String arenaObjectName, String link) {
		this.links.put(arenaObjectName, link);
	}
	
	public boolean buildRole() {
		if(this.isValid()) {
			Role role = new RoleCore(this.roleName, this.links);
			for(Entry<SkillActivation, Skill> skill : this.skills.entrySet()) {
				role.addSkill(skill.getValue());
			}
			((CodariCore) CodariI.INSTANCE).getRoleManager().putRole(this.roleName, role);
			return true;
		}
		return false;
	}
	
	private boolean isValid() {
		if(!((CodariCore) CodariI.INSTANCE).getRoleManager().containsRole(this.roleName)) {
			return true;
		}
		return false;
	}
}
