package com.codari.arenacore.players.role;

import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arena5.players.role.Role;
import com.codari.arena5.players.skills.Skill;
import com.codari.arena5.players.skills.SkillActivation;
import com.codari.arena5.players.skills.SkillName;
import com.codari.arenacore.LibraryCore;

@SerializableAs("Random_Thing_No_One_Will_See_But_Used_For_Future_Proofing_If_Package_Names_Ever_Change")
public class RoleCore implements Role, ConfigurationSerializable {
	private Map<SkillActivation, Skill> skills;
	private String name;
	private Map<String, String> links;
	
	public RoleCore(String name, Map<String, String> links) {
		this.name = name;
		this.skills = new EnumMap<>(SkillActivation.class);
		if(links != null) {
			this.links = new LinkedHashMap<>(links);
		}
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
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("name", this.name);
		for (Entry<SkillActivation, Skill> e : this.skills.entrySet()) {
			SkillName skillName = e.getValue().getClass().getAnnotation(SkillName.class);
			String name = skillName.value();
			result.put(e.getKey().toString(), name);
		}
		result.putAll(this.links);
		return result;
	}
	
	public RoleCore(Map<String, Object> args) {
		this.name = (String) args.remove("name");
		this.skills = new EnumMap<>(SkillActivation.class);
		for (SkillActivation a : SkillActivation.values()) {
			final Skill skill = ((LibraryCore) Codari.getLibrary()).createSkill((String) args.remove(a.toString()));
			this.skills.put(a, skill);
		}
		this.links = new LinkedHashMap<>(links);
		for (Entry<String, Object> e : args.entrySet()) {
			if (e.getValue() instanceof String) {
				this.links.put(e.getKey(), (String) e.getValue());
			}
		}
	}
}
