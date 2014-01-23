package com.codari.arenacore.arena.objects;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.arena5.objects.ArenaObjectName;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.players.combatants.Combatant;

@ArenaObjectName("Role Selection Object")
public class RoleSelectionObject implements ImmediatePersistentObject {
	private static final long serialVersionUID = 3577897723052477603L;
	private transient BlockState quartzBlockState;
	private Material quartzBlockMaterial = Material.QUARTZ_BLOCK;
	
	private Map<String, RoleData> roleDatas;
	private Location location;
	private final String name;
	
	public static final String INVENTORY_NAME = "Role Selection";
	private Inventory inventory;

	public RoleSelectionObject(Location location) {
		this.location = location;
		ArenaObjectName arenaObjectName = this.getClass().getAnnotation(ArenaObjectName.class);
		this.name = arenaObjectName.value();
		this.quartzBlockState = location.getBlock().getState();
	}
	
	public void setRoleDatas(Map<String, RoleData> roleDatas) {
		this.roleDatas = roleDatas;
		this.setupInventory();
	}
	
	@Override
	public void reveal() {
		this.quartzBlockState.getBlock().setType(quartzBlockMaterial);
	}

	@Override
	public void hide() {
		this.quartzBlockState.update(true);	
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	@Override
	public void interact(Combatant combatant) {		
		Player player = combatant.getPlayer();
		player.openInventory(this.inventory);
	}
	
	public void adjustRoleIcons(Combatant combatant, String newRoleName) {
		if(combatant.getRole().getName().equals("Non Combatant")) {
			this.removeRole(newRoleName);
		} else {
			this.swapRole(combatant.getRole().getName(), newRoleName);
		}
	}
	
	public int getRoleCounter(String roleName) {
		return this.roleDatas.get(roleName).getCounter();
	}	
	
	private void addRole(String oldRoleName) {
		this.roleDatas.get(oldRoleName).increment();
		ItemStack[] itemStacks = this.inventory.getContents();
		for(ItemStack itemStack : itemStacks) {
			if(itemStack.getItemMeta().getDisplayName().equals(oldRoleName)) {
				itemStack.setAmount(itemStack.getAmount() + 1);
			}
		}
		this.inventory.setContents(itemStacks);
		this.refreshInventory();
	}
	
	private void removeRole(String newRoleName) {
		this.roleDatas.get(newRoleName).decrement();	
		ItemStack[] itemStacks = this.inventory.getContents();
		for(ItemStack itemStack : itemStacks) {
			if(itemStack.getItemMeta().getDisplayName().equals(newRoleName)) {
				itemStack.setAmount(itemStack.getAmount() - 1);
			}
		}
		this.inventory.setContents(itemStacks);
		this.refreshInventory();
	}
	
	private void swapRole(String oldRoleName, String newRoleName) {
		this.addRole(oldRoleName);
		this.removeRole(newRoleName);
	}
	
	private void setupInventory() {
		int size = this.roleDatas.size();
		size = size < 0 ? 0 : (size > 54 ? 54 : (size % 9 != 0 ? (((size + 8) / 9) * 9) : size));
		this.inventory = Bukkit.createInventory(null, size, INVENTORY_NAME);
		int counter = 0;
		for(String roleName : this.roleDatas.keySet()) {
			int numberOfRoles = this.roleDatas.get(roleName).getCounter();
			this.inventory.setItem(counter++, this.createIcon(roleName, numberOfRoles));
		}
	}
	
	private ItemStack createIcon(String roleName, int numberOfRoles) {
		ItemStack roleItem = new ItemStack(Material.EXPLOSIVE_MINECART);
		ItemMeta itemMeta = roleItem.getItemMeta();
		itemMeta.setDisplayName(roleName);
		roleItem.setItemMeta(itemMeta);
		roleItem.setAmount(numberOfRoles);
		return roleItem;
	}
	
	@SuppressWarnings("deprecation")
	private void refreshInventory() {
		for(HumanEntity humanEntity : this.inventory.getViewers()) {
			if(humanEntity instanceof Player) {
				((Player) humanEntity).updateInventory();
			}
		}
	}
}
