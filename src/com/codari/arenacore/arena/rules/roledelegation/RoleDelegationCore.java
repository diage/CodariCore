package com.codari.arenacore.arena.rules.roledelegation;

import java.util.Collection;
import java.util.List;

import com.codari.arena5.arena.rules.roledelegation.RoleDelegation;
import com.codari.arena5.arena.rules.roledelegation.RoleDelegationType;
import com.codari.arena5.objects.persistant.RoleSelectionObject;

public class RoleDelegationCore implements RoleDelegation {
	private static final long serialVersionUID = 4448694982876919961L;
	private RoleDelegationType roleDelegationType;
	private List<RoleSelectionObject> roleSelectionObjects;
	
	@Override
	public void setRoleDelegationType(RoleDelegationType roleDelegationType) {
		this.roleDelegationType = roleDelegationType;
	}

	@Override
	public void addRoleSelectionObject(RoleSelectionObject roleSelectionObject) {
		this.roleDelegationType = RoleDelegationType.SELECT_START;
		this.roleSelectionObjects.add(roleSelectionObject);
	}

	@Override
	public RoleDelegationType getRoleDelegationTechnique() {
		return this.roleDelegationType;
	}

	@Override
	public Collection<RoleSelectionObject> getRoleSelectionObjects() {
		return this.roleSelectionObjects;
	}

	@Override
	public boolean isValidRoleDelegation() {
		switch(roleDelegationType) {
		case SELECT_START:
			if(roleSelectionObjects.size() == 0) {
				return false;
			}
		case RANDOM:
			return true;
		default:
			return false;
		}
	}

}
