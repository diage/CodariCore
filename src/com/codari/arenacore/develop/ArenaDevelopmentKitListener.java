package com.codari.arenacore.develop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.codari.api5.Codari;
import com.codari.arena5.players.combatants.Combatant;

public class ArenaDevelopmentKitListener implements Listener {
	//-----Fields-----//
	//---Inventory Slots---//
	private final int ITEM_SPAWNER_SLOT = ArenaDevelopmentKit.INVENTORY_STARTING_PLACEMENT_SLOT;
	private final int DIAMOND_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 2;
	private final int EMERALD_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 3;
	private final int GOLD_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 4;
	private final int IRON_OBJECTIVE_POINT = ITEM_SPAWNER_SLOT + 5;
	private final int EXPLOSION_TRAP = ITEM_SPAWNER_SLOT + 6;
	private final int FIRE_TRAP = ITEM_SPAWNER_SLOT + 7;
	private final int POISON_SNARE_TRAP = ITEM_SPAWNER_SLOT + 8;
	private final int GATE = ITEM_SPAWNER_SLOT + 9;
	
	@EventHandler()
	public void onPlayerRightClickTeam(InventoryClickEvent e) {
		Combatant combatant = Codari.INSTANCE.getArenaManager().getCombatant((Player)e.getWhoClicked());
		if(Codari.INSTANCE.getArenaManager().getTeam(combatant) == null) {
			if(e.isRightClick()) {
				int clickedSlot = e.getSlot();
				switch(clickedSlot) {
				case(ITEM_SPAWNER_SLOT):
					//Set Item Spawner
					break;
				case(DIAMOND_OBJECTIVE_POINT):
					//Set Diamond Objective Point
					break;
				case(EMERALD_OBJECTIVE_POINT):
					//Set Emerald Objective Point
					break;
				case(GOLD_OBJECTIVE_POINT):
					//Set Gold Objective Point
					break;
				case(IRON_OBJECTIVE_POINT):
					//Set Iron Objective Point
					break;
				case(EXPLOSION_TRAP):
					//Set Explosion Trap
					break;
				case(FIRE_TRAP):
					//Set Fire Trap
					break;
				case(POISON_SNARE_TRAP):
					//Set Poison Snare Trap
					break;
				case(GATE):
					//Set Gate
					break;
				}
			}	
		}
	}
}
