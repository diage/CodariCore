package com.codari.arenacore.arena.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.util.org.apache.commons.lang3.RandomStringUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.codari.api5.CodariI;
import com.codari.api5.annotations.ArenaObjectName;
import com.codari.arena5.objects.persistant.ImmediatePersistentObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.combatants.CombatantCore;

@ArenaObjectName(RoleSelectionObject.OBJECT_NAME)
public class RoleSelectionObject implements ImmediatePersistentObject {
	private static final long serialVersionUID = 3577897723052477603L;
	private transient BlockState quartzBlockState;
	private Material quartzBlockMaterial = Material.QUARTZ_BLOCK;

	private Map<String, RoleData> roleDatas;
	private Location location;
	List<RoleData> remainingRoles;

	public static final String INVENTORY_NAME = "Role Selection";
	public static final String OBJECT_NAME = "Role Selection Object";
	private Inventory inventory;

	public static final String META_DATA_STRING = RandomStringUtils.randomAscii(69);

	public RoleSelectionObject(Location location, Map<String, RoleData> roleDatas) {
		this.location = location;
		this.quartzBlockState = location.getBlock().getState();
		this.remainingRoles = new ArrayList<>();
		if(roleDatas != null) {
			this.roleDatas = roleDatas;
			int size = this.roleDatas.size();
			size = size < 0 ? 0 : (size > 54 ? 54 : (size % 9 != 0 ? (((size + 8) / 9) * 9) : size));
			this.inventory = Bukkit.createInventory(null, size, INVENTORY_NAME);
		}
	}

	@Override
	public void reveal() {
		this.quartzBlockState.getBlock().setType(quartzBlockMaterial);
	}

	@Override
	public void activate() {	
		if(!this.quartzBlockState.hasMetadata(META_DATA_STRING)) {
			this.quartzBlockState.setMetadata(META_DATA_STRING, new FixedMetadataValue(CodariI.INSTANCE, this));
			this.remainingRoles.clear();
			this.initializeInventory();
		}
	}

	@Override
	public void hide() {
		this.quartzBlockState.update(true);	
		this.deactivate();
	}
	
	@Override
	public Collection<BlockState> getAffectedBlocks() {
		Collection<BlockState> affectedBlocks = new ArrayList<>();
		affectedBlocks.add(this.quartzBlockState);
		return affectedBlocks;
	}

	@Override
	public String getName() {
		return OBJECT_NAME;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	@Override
	public void interact(Combatant combatant) {		
		if(this.inventory != null) {
			Player player = combatant.getPlayer();
			player.openInventory(this.inventory);
		}
	}

	public void adjustRoleIcons(Combatant combatant, String newRoleName) {
		if(combatant.getRole().getName().equals(CombatantCore.NON_COMBATANT)) {
			this.removeRole(newRoleName);
		} else {
			this.swapRole(combatant.getRole().getName(), newRoleName);
		}
	}
	
	public List<RoleData> getAllRoles() {
		List<RoleData> allRoleDatas = new ArrayList<>();
		for(Entry<String, RoleData> roleData : this.roleDatas.entrySet()) {
			allRoleDatas.add(roleData.getValue());
		}
		return allRoleDatas;
	}

	public List<RoleData> getRemainingRoles() {
		return this.remainingRoles;
	}

	private void storeRemainingRoles() {
		if(this.roleDatas.size() > 0) {
			for(String roleName : this.roleDatas.keySet()) {
				if(this.roleDatas.get(roleName).getCounter() > 0 || this.roleDatas.get(roleName).getCounter() == RoleData.INFINITE) {
					this.remainingRoles.add(new RoleData(this.roleDatas.get(roleName)));
				}
			}
		}
	}

	private void resetRoleDatas() {
		for(String roleName : this.roleDatas.keySet()) {
			this.roleDatas.get(roleName).resetToInitialSettings();
		}
	}

	private void deactivate() {	
		if(this.quartzBlockState.hasMetadata(META_DATA_STRING)) {
			this.quartzBlockState.removeMetadata(META_DATA_STRING, CodariI.INSTANCE);
			this.storeRemainingRoles();
			this.resetRoleDatas();
		}
		this.closeInventoryViewers();
	}

	private void addRole(String oldRoleName) {
		if(!roleDatas.get(oldRoleName).isInfinite()) {
			ItemStack[] itemStacks = this.inventory.getContents();
			boolean itemFound = false;
			this.roleDatas.get(oldRoleName).increment();
			for(ItemStack itemStack : itemStacks) {
				if(itemStack != null) {
					if(itemStack.getItemMeta().getDisplayName().equals(oldRoleName)) {
						itemStack.setAmount(itemStack.getAmount() + 1);
						itemFound = true;
						break;
					}
				}
			}
			if(itemFound) {
				this.inventory.setContents(itemStacks);
			} else if(this.inventory.firstEmpty() != -1) {
				this.inventory.setItem(this.inventory.firstEmpty(), this.createIcon(oldRoleName, 1));
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + "Failed to add Role Icon to Role Selection Object - There are no available slots!"); //TODO
			}
			this.refreshInventory();
		}
	}

	private void removeRole(String newRoleName) {
		if(!roleDatas.get(newRoleName).isInfinite()) {
			ItemStack[] itemStacks = this.inventory.getContents();
			this.roleDatas.get(newRoleName).decrement();	
			for(ItemStack itemStack : itemStacks) {
				if(itemStack != null) {
					if(itemStack.getItemMeta().getDisplayName().equals(newRoleName)) {
						itemStack.setAmount(itemStack.getAmount() - 1);
						break;
					}
				}
			}
			this.inventory.setContents(itemStacks);
			this.refreshInventory();
		}
	}

	private void swapRole(String oldRoleName, String newRoleName) {
		this.addRole(oldRoleName);
		this.removeRole(newRoleName);
	}

	private void initializeInventory() {
		this.inventory.clear();
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
		if(numberOfRoles != RoleData.INFINITE) {
			roleItem.setAmount(numberOfRoles);
		} 
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

	private void closeInventoryViewers() {
		if(this.inventory != null) {
			Iterator<HumanEntity> iterator = this.inventory.getViewers().iterator();
			while(iterator.hasNext()) {
				//Closes the inventory for all the players viewing it
				iterator.next().closeInventory();
			}
		}
	}

}
