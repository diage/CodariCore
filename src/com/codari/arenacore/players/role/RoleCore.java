package com.codari.arenacore.players.role;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;

public class RoleCore implements Role {
	private Map<SkillActivation, Skill> skills;
	private String name;
	private Map<String, String> links;
	
	public RoleCore(String name, Map<String, String> links) {
		this.name = name;
		this.skills = new HashMap<>();
		this.links = links;
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
	public boolean skill(Combatant combatant) {
		if(this.skills.containsKey(SkillActivation.SKILL)) {
			this.skills.get(SkillActivation.SKILL).activateSkill(combatant);
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
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RoleCore) {
			return this.getName().equals(((RoleCore) obj).getName());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getName()).build();
	}
	
	@Override
	public String getLink(String arenaObjectName) {
		if(this.links.containsKey(arenaObjectName)) {
			return this.links.get(arenaObjectName);
		}
		Bukkit.broadcastMessage(ChatColor.RED + "There is a role that is trying to interact w/ an object but there is no link!");	//TODO
		return null;
	}
	
	public Map<String, String> getLinks() {
		return this.links;
	}
	
	public Set<String> getObjectsWithLinks() {
		return this.links.keySet();
	}
}
