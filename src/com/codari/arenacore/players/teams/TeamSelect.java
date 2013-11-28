package com.codari.arenacore.players.teams;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.codari.api5.CodariI;
import com.codari.arena5.players.combatants.Combatant;


public class TeamSelect implements Listener {
	//-----Fields-----//
	private final ItemStack RANDOM_TEAM_SELECT = new ItemStack(Material.BOOK);
	private int randomTeamSelectItemStack = 9;
	
	private final ItemStack BLUE_TEAM_SELECT = new ItemStack(Material.BOOK);
	private int blueTeamSelectItemStack = 10;
	
	private final ItemStack RED_TEAM_SELECT = new ItemStack(Material.BOOK);
	private int redTeamSelectItemStack = 11;
	
	//-----Events-----//
	@EventHandler()
	public void onPlayerLogin(PlayerLoginEvent e) {
		ItemMeta randomItemMeta = RANDOM_TEAM_SELECT.getItemMeta();
		randomItemMeta.setDisplayName("Join a random team.");
		RANDOM_TEAM_SELECT.setItemMeta(randomItemMeta);
		
		ItemMeta blueItemMeta = BLUE_TEAM_SELECT.getItemMeta();
		blueItemMeta.setDisplayName("Join the blue team.");
		BLUE_TEAM_SELECT.setItemMeta(blueItemMeta);
		
		ItemMeta redItemMeta = RED_TEAM_SELECT.getItemMeta();
		redItemMeta.setDisplayName("Join the red team.");
		RED_TEAM_SELECT.setItemMeta(redItemMeta);
		
		Player player = e.getPlayer();
		player.getInventory().setItem(randomTeamSelectItemStack, RANDOM_TEAM_SELECT);
		player.getInventory().setItem(blueTeamSelectItemStack, BLUE_TEAM_SELECT);
		player.getInventory().setItem(redTeamSelectItemStack, RED_TEAM_SELECT);
	}
	
	@EventHandler()
	public void onPlayerRightClickTeam(InventoryClickEvent e) {
		Combatant combatant = CodariI.INSTANCE.getArenaManager().getCombatant((Player)e.getWhoClicked());
		if(CodariI.INSTANCE.getArenaManager().getTeam(combatant) == null) {
			if(e.isRightClick()) {
				if(e.getSlot() == randomTeamSelectItemStack) {
					joinRandomTeam(combatant);
				} else if(e.getSlot() == blueTeamSelectItemStack) {
					joinBlueTeam(combatant);
				} else if(e.getSlot() == redTeamSelectItemStack) {
					joinRedTeam(combatant);
				}
			}
		}
	}
	
	private void joinRandomTeam(Combatant combatant) {
		Random random = new Random(System.currentTimeMillis());
		switch(random.nextInt(2)) {
		case 0: 
			joinBlueTeam(combatant);
			break;
		case 1:
			joinRedTeam(combatant);
			break;
		default:
			joinBlueTeam(combatant);
		}
	}
	
	private void joinBlueTeam(Combatant combatant) {
		//combatant.setTeam(TeamColor.BLUE);
		Player player = combatant.getPlayerReference().getPlayer();
		player.sendMessage(ChatColor.BLUE + "You are on the blue team.");
	}
	
	private void joinRedTeam(Combatant combatant) {
		//combatant.setTeam(TeamColor.RED);
		Player player = combatant.getPlayerReference().getPlayer();
		player.sendMessage(ChatColor.RED + "You are on the red team.");
	}
}
