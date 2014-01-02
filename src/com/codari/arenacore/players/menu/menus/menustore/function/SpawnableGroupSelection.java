package com.codari.arenacore.players.menu.menus.menustore.function;

import java.util.Iterator;
import java.util.Set;

import com.codari.arena5.objects.spawnable.RandomSpawnableObject;
import com.codari.arena5.players.combatants.Combatant;
import com.codari.arenacore.players.builders.kit.Kit;
import com.codari.arenacore.players.menu.icons.iconstore.common.BackIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.NextIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.PreviousIcon;
import com.codari.arenacore.players.menu.icons.iconstore.common.SpawnableGroupIcon;
import com.codari.arenacore.players.menu.icons.structure.Icon;
import com.codari.arenacore.players.menu.menus.FunctionMenu;
import com.codari.arenacore.players.menu.slots.FunctionMenuSlot;

public class SpawnableGroupSelection extends FunctionMenu {

	public SpawnableGroupSelection(Combatant combatant, Kit kit, RandomSpawnableObject arenaObject, BackIcon backIcon) {
		super(combatant);
		this.addIcons(combatant, kit, arenaObject, backIcon);
	}
	
	private SpawnableGroupSelection(Combatant combatant, Kit kit, RandomSpawnableObject arenaObject, BackIcon backIcon, Icon previous, Set<String> randomSpawnableGroupNames) {
		super(combatant);
		this.addIcons(combatant, kit, arenaObject, backIcon, previous, randomSpawnableGroupNames);
	}
	
	private void addIcons(Combatant combatant, Kit kit, RandomSpawnableObject arenaObject, BackIcon backIcon) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		
		Set<String> randomSpawnableGroupNames = kit.getArenaBuilder().getRandomSpawnablesCopyMap().keySet();
		
		int i = 0;
		Iterator<String> iterator = randomSpawnableGroupNames.iterator();
		while(i < 10 && iterator.hasNext()) {
			this.addSpawnableGroupIcon(combatant, kit, arenaObject, iterator.next());
			
			iterator.remove();
			i++;
		}
		
		if(randomSpawnableGroupNames.size() > 0) {
			Icon prevIcon = new PreviousIcon(combatant, this);
			Icon nextIcon = new NextIcon(combatant, new SpawnableGroupSelection(combatant, kit, arenaObject, backIcon, prevIcon, randomSpawnableGroupNames));
			this.addNextIcon(nextIcon);
		}
 	}
	
	private void addIcons(Combatant combatant, Kit kit, RandomSpawnableObject arenaObject, BackIcon backIcon, Icon previous, Set<String> randomSpawnableGroupNames) {
		super.setSlot(FunctionMenuSlot.C_ONE, backIcon);
		this.addPreviousIcon(previous);
		
		int i = 0;
		Iterator<String> iterator = randomSpawnableGroupNames.iterator();
		while(i < 10 && iterator.hasNext()) {
			this.addSpawnableGroupIcon(combatant, kit, arenaObject, iterator.next());
			
			iterator.remove();
			i++;
		}
		
		if(randomSpawnableGroupNames.size() > 0) {
			Icon prevIcon = new PreviousIcon(combatant, this);
			Icon nextIcon = new NextIcon(combatant, new SpawnableGroupSelection(combatant, kit, arenaObject, backIcon, prevIcon, randomSpawnableGroupNames));
			this.addNextIcon(nextIcon);
		}
	} 
	
	private void addSpawnableGroupIcon(Combatant combatant, Kit kit, RandomSpawnableObject arenaObject, String groupName) {
		if(super.getNextAvailableSlot() != FunctionMenuSlot.NO_SLOT) {
			super.setSlot(super.getNextAvailableSlot(), new SpawnableGroupIcon(combatant, new SlotSelection(combatant, kit, groupName, arenaObject, new BackIcon(combatant, this)), groupName));
		}
	}

	private void addNextIcon(Icon icon) {
		super.setSlot(FunctionMenuSlot.C_FIVE, icon);
	}
	
	private void addPreviousIcon(Icon icon) {
		super.setSlot(FunctionMenuSlot.C_TWO, icon);
	}	
}
