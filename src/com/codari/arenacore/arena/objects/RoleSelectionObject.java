package com.codari.arenacore.arena.objects;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.players.combatants.Combatant;

@ArenaObjectName("Role Selection Object")
public class RoleSelectionObject implements ImmediatePersistentObject {
	private static final long serialVersionUID = 3577897723052477603L;
	private transient BlockState redStoneBlockState;
	private Material quartzBlockMaterial = Material.QUARTZ_BLOCK;
	
	private Map<String, RoleData> roleDatas;
	private Location location;
	private final String name;
	

	public RoleSelectionObject(Location location) {
		this.location = location;
		ArenaObjectName arenaObjectName = this.getClass().getAnnotation(ArenaObjectName.class);
		this.name = arenaObjectName.value();
	}
	
	public void setRoleDatas(Map<String, RoleData> roleDatas) {
		this.roleDatas = roleDatas;
	}
	
	@Override
	public void reveal() {
		this.redStoneBlockState.getBlock().setType(quartzBlockMaterial);
	}

	@Override
	public void hide() {
		this.redStoneBlockState.update(true);	
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	@SuppressWarnings("null")
	@Override
	public void interact() {
		/* 
		 * FIXME - Needs to open up an inventory, needs to have interactable icons.
		 * When these icons are interacted with, needs to modify the current number of roles and change the role of the player.
		 *  */
		Combatant combatant = null;
		if(combatant.getRole().getName().equals("Non Combatant")) {
			this.removeRole(null);
		} else {
			this.swapRole(combatant.getRole().getName(), null);
		}
	}
	
	private void removeRole(String newRoleName) {
		this.roleDatas.get(newRoleName).decrement();
	}
	
	private void swapRole(String oldRoleName, String newRoleName) {
		this.roleDatas.get(oldRoleName).increment();
		this.roleDatas.get(newRoleName).decrement();
	}
	
	public int getRoleCounter(String roleName) {
		return this.roleDatas.get(roleName).getCounter();
	}
}
