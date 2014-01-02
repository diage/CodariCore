package com.codari.arenacore.players.menu.menus.menustore.function;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.PersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.ArenaObjectFixedIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.ArenaObjectPersistentIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.ArenaObjectRandomIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.CreateSpawnableGroupIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kits.kit.options.spawnablegroup.EditSpawnableGroupIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroup extends FunctionMenu {

	public SpawnableGroup(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon);
	}
	
	private SpawnableGroup(Combatant combatant, Kit kit, BackIcon backIcon, Icon previous, ArrayList<ArenaObject> arenaObjects) {
		super(combatant);
		this.addIcons(combatant, kit, backIcon, previous, arenaObjects);
	}

	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_THREE, new EditSpawnableGroupIcon(combatant, new SpawnableGroupEdit(combatant, kit, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_FOUR, new CreateSpawnableGroupIcon(combatant, new SpawnableGroupCreate(combatant, kit, new BackIcon(combatant, this))));
		
		ArrayList<ArenaObject> arenaObjects = kit.getArenaBuilder().getArenaObjectsCopyList();
		
		int i = 0;
		Iterator<ArenaObject> iterator = arenaObjects.iterator();
		while(i < 10 && iterator.hasNext()) {
			if(iterator.next() instanceof RandomSpawnableObject) {
				this.addRandomSpawnableObjectIcon(combatant, kit, ((RandomSpawnableObject)iterator.next()));
			} else if(iterator.next() instanceof FixedSpawnableObject) {
				this.addFixedSpawnableObjectIcon(combatant, kit, ((FixedSpawnableObject)iterator.next()));
			} else if(iterator.next() instanceof PersistentObject) {
				this.addPersistentObjectIcon(combatant, kit, ((PersistentObject)iterator.next()));
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + iterator.next().getClass().getSimpleName() + " was unable to be registered because an icon was not created for its Arena Object Type!");
			}
			iterator.remove();
			i++;
		}
		
		if(arenaObjects.size() > 0) {
			Icon prevIcon = new PreviousIcon(combatant, this);
			Icon nextIcon = new NextIcon(combatant, new SpawnableGroup(combatant, kit, backIcon, prevIcon, arenaObjects));
			this.addNextIcon(nextIcon);
		}
	}
	
	private void addIcons(Combatant combatant, Kit kit, BackIcon backIcon, Icon previous, ArrayList<ArenaObject> arenaObjects) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		this.addPreviousIcon(previous);
		super.setSlot(FunctionMenuSlot.C_THREE, new EditSpawnableGroupIcon(combatant, new SpawnableGroupEdit(combatant, kit, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_FOUR, new CreateSpawnableGroupIcon(combatant, new SpawnableGroupCreate(combatant, kit, new BackIcon(combatant, this))));
		
		int i = 0;
		Iterator<ArenaObject> iterator = arenaObjects.iterator();
		while(i < 10 && iterator.hasNext()) {
			if(iterator.next() instanceof RandomSpawnableObject) {
				this.addRandomSpawnableObjectIcon(combatant, kit, ((RandomSpawnableObject)iterator.next()));
			} else if(iterator.next() instanceof FixedSpawnableObject) {
				this.addFixedSpawnableObjectIcon(combatant, kit, ((FixedSpawnableObject)iterator.next()));
			} else if(iterator.next() instanceof PersistentObject) {
				this.addPersistentObjectIcon(combatant, kit, ((PersistentObject)iterator.next()));
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + iterator.next().getClass().getSimpleName() + " was unable to be registered because an icon was not created for its Arena Object Type!");
			}
			iterator.remove();
			i++;
		}
		
		if(arenaObjects.size() > 0) {
			Icon prevIcon = new PreviousIcon(combatant, this);
			Icon nextIcon = new NextIcon(combatant, new SpawnableGroup(combatant, kit, backIcon, prevIcon, arenaObjects));
			this.addNextIcon(nextIcon);
		}
	}
	
	private void addRandomSpawnableObjectIcon(Combatant combatant, Kit kit, RandomSpawnableObject arenaObject) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new ArenaObjectRandomIcon(combatant, new SpawnableGroupSelection(combatant, kit, arenaObject, new BackIcon(combatant, this)), arenaObject));
		}
	}
	
	private void addFixedSpawnableObjectIcon(Combatant combatant, Kit kit, FixedSpawnableObject arenaObject) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new ArenaObjectFixedIcon(combatant, new FixedSpawnableTimeSelection(combatant, kit, arenaObject, new BackIcon(combatant, this)), arenaObject));
		}
	}
	private void addPersistentObjectIcon(Combatant combatant, Kit kit, PersistentObject arenaObject) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new ArenaObjectPersistentIcon(combatant, new PersistentObjectSettings(combatant, kit, arenaObject, new BackIcon(combatant, this)), arenaObject));
		}
	}
	
	private void addNextIcon(Icon icon) {
		super.setSlot(FunctionMenuSlot.C_FIVE, icon);
	}
	
	private void addPreviousIcon(Icon icon) {
		super.setSlot(FunctionMenuSlot.C_TWO, icon);
	}	
}
