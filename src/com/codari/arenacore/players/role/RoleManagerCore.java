package com.codari.arenacore.players.role;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.codari.arena5.players.role.Role;

public class RoleManagerCore {
	private Map<String, Role> roles;
	
	public RoleManagerCore() {
		this.roles = new HashMap<>();
	}
	
	public boolean putRole(String roleName, Role role) {
		if(!this.roles.containsKey(roleName)) {
			this.roles.put(roleName, role);
			return true;
		}
		return false;
	}
	
	public void removeRole(String roleName) {
		if(this.roles.containsKey(roleName)) {
			this.roles.remove(roleName);
		}
	}

	public Role getRole(String roleName) {
		if(this.roles.containsKey(roleName)) {
			return this.roles.get(roleName);
		}
		return null;
	}
	
	public Set<String> getRoles() {
		return this.roles.keySet();
	}

	public boolean containsRole(String roleName) {
		if(this.roles.containsKey(roleName)) {
			return true;
		}
		return false;
	}
	
}
