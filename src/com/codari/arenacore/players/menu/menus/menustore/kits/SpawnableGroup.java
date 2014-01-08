package com.codari.arenacore.players.menu.menus.menustore.kits;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.codari.api5.Codari;
import com.codari.arena5.objects.ArenaObject;
import com.codari.arena5.objects.persistant.PersistentObject;
import com.codari.arena5.objects.spawnable.FixedSpawnableObject;
import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.LibraryCore;
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
	private SpawnableGroup nextPage;
	private SpawnableGroupSelection spawnableGroupSelection;
	
	public SpawnableGroup(Combatant combatant, Kit kit, BackIcon backIcon) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_THREE, new EditSpawnableGroupIcon(combatant, new SpawnableGroupEditSelection(combatant, kit, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_FOUR, new CreateSpawnableGroupIcon(combatant, new SpawnableGroupCreate(combatant, kit, new BackIcon(combatant, this))));			
		this.spawnableGroupSelection = new SpawnableGroupSelection(combatant, kit, new BackIcon(combatant, this));
		for(Entry<String, Class<? extends ArenaObject>> objectEntry : ((LibraryCore)Codari.getLibrary()).getObjectEntrys()) {
			this.addArenaObject(combatant, kit, objectEntry, backIcon);
		}
	}
	
	/* This will construct any further needed pages for Spawnable Group. */
	private SpawnableGroup(Combatant combatant, Kit kit, Icon previous, BackIcon backIcon, SpawnableGroupSelection spawnableGroupSelection) {
		super(combatant);
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		super.setSlot(FunctionMenuSlot.C_TWO, previous);
		super.setSlot(FunctionMenuSlot.C_THREE, new EditSpawnableGroupIcon(combatant, new SpawnableGroupEditSelection(combatant, kit, new BackIcon(combatant, this))));
		super.setSlot(FunctionMenuSlot.C_FOUR, new CreateSpawnableGroupIcon(combatant, new SpawnableGroupCreate(combatant, kit, new BackIcon(combatant, this))));
		this.spawnableGroupSelection = spawnableGroupSelection;
	}
	
	private void addArenaObject(Combatant combatant, Kit kit, Entry<String, Class<? extends ArenaObject>> objectEntry, BackIcon backIcon) {
		String arenaObjectName = objectEntry.getKey();
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			if(RandomSpawnableObject.class.isAssignableFrom(objectEntry.getValue())) {
				super.setSlot(super.getNextAvailableSlot(), new ArenaObjectRandomIcon(combatant, this.spawnableGroupSelection, arenaObjectName));
			} else if(FixedSpawnableObject.class.isAssignableFrom(objectEntry.getValue())) {
				super.setSlot(super.getNextAvailableSlot(), new ArenaObjectFixedIcon(combatant, new FixedSpawnableTimeSelection(combatant, kit, arenaObjectName, new BackIcon(combatant, this)), arenaObjectName));
			} else if(PersistentObject.class.isAssignableFrom(objectEntry.getValue())) {
				super.setSlot(super.getNextAvailableSlot(), new ArenaObjectPersistentIcon(combatant, new PersistentObjectSettings(combatant, kit, arenaObjectName, new BackIcon(combatant, this)), arenaObjectName));
			} else {
				Bukkit.broadcastMessage(ChatColor.RED + arenaObjectName + " was unable to be registered because an icon was not created for its Arena Object Type!"); //TODO
			}
		} else {
			if(this.nextPage != null) {
				this.nextPage.addArenaObject(combatant, kit, objectEntry, backIcon);
			} else {
				this.addNextPage(combatant, kit, backIcon);
				this.nextPage.addArenaObject(combatant, kit, objectEntry, backIcon);
			}
		}
	}
	
	private void addNextPage(Combatant combatant, Kit kit, BackIcon backIcon) {
		Icon prevIcon = new PreviousIcon(combatant, this);
		this.nextPage = new SpawnableGroup(combatant, kit, prevIcon, backIcon, this.spawnableGroupSelection);
		super.setSlot(FunctionMenuSlot.C_FIVE, new NextIcon(combatant, this.nextPage));
	}
}
