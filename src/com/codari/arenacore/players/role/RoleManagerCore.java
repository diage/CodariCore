package com.codari.arenacore.players.role;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.codari.api5.io.CodariSerialization;
import com.codari.apicore.CodariCore;
import com.codari.arena5.players.role.Role;

public class RoleManagerCore {
	private Map<String, Role> roles;
	private final File roleDir;
	
	public RoleManagerCore() {
		this.roles = new HashMap<>();
		ConfigurationSerialization.registerClass(RoleCore.class);
		this.roleDir = new File(CodariCore.instance().getDataFolder(), "Roll");
		if (this.roleDir.exists()) {
			for (File file : this.roleDir.listFiles()) {
				try {
					this.loadRole(file);
				} catch (Exception ex) {
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "Couldnt find potato in " + file, ex);
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
					CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				}
			}
		} else {
			this.roleDir.mkdirs();
		}
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
	
	public void saveRoles() {
		for (Entry<String, Role> e : this.roles.entrySet()) {
			try {
				File file = new File(this.roleDir, e.getKey());
				CodariSerialization.serialize(file, e.getValue());
			} catch (Exception ex) {
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "Couldnt save potato in " + e.getValue(), ex);
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
				CodariCore.instance().getLogger().log(Level.SEVERE, "||||||||||| POTATO ERROR ||||||||||||||");
			}
		}
	}
	
	public boolean saveRole(String name) {
		if (!this.roles.containsKey(name)) {
			CodariCore.instance().getLogger().log(Level.WARNING, "No role saved under the name " + name);
			return false;
		}
		File file = new File(this.roleDir, name);
		CodariSerialization.serialize(file, this.getRole(name));
		return true;
	}
	
	public boolean loadRole(File file) {
		String name = file.getName();
		if (this.roles.containsKey(name)) {
			CodariCore.instance().getLogger().log(Level.WARNING, "Can not role to the name "
					+ name + " as a arena builder already exists with that name");
			return false;
		}
		RoleCore role = (RoleCore) CodariSerialization.deserialize(file);
		this.roles.put(name, role);
		return true;
	}
}
