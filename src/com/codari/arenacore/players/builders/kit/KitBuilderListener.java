package com.codari.arenacore.players.builders.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codari.api5.Codari;
import com.codari.arena5.arena.rules.wincondition.WinCondition;
import com.codari.arenacore.LibraryCore;
import com.codari.arenacore.players.combatants.CombatantCore;
import com.codari.arenacore.players.menu.events.IconHoverUpdateEvent;
import com.codari.arenacore.players.menu.events.IconMenuClickEvent;
import com.codari.arenacore.players.menu.events.IconRequestEvent;
import com.codari.arenacore.players.menu.icons.HoverIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SelectKitBuilderNameIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SetNumberOfTeamsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.SetTeamSizeIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationMinutesIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationSecondsIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.matchduration.MatchDurationTicksIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.WinConditionSelectionIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.timesettings.SetWinConditionMinuteIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.timesettings.SetWinConditionSecondIcon;
import com.codari.arenacore.players.menu.icons.iconstore.kitbuilders.creation.winconditions.selection.timesettings.SetWinConditionTickIcon;

public class KitBuilderListener implements Listener {
	public static Map<String, KitBuilder> currentKitBuilders = new HashMap<>();

	@EventHandler()
	private void selectKitBuilderName(IconRequestEvent e) {
		if(e.getIcon() instanceof SelectKitBuilderNameIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(!((CombatantCore) e.getIcon().getCombatant()).getKitManager().getKitBuilders().containsKey(e.getPlayerInput())) {
				KitBuilder kitBuilder = new KitBuilder(e.getPlayerInput());
				currentKitBuilders.put(player.getName(), kitBuilder);
				player.sendMessage(ChatColor.BLUE + "Kit Builder name changed to " + e.getPlayerInput() + ". Kit Builder settings reset.");	
			} else {
				player.sendMessage(ChatColor.RED + e.getPlayerInput() + " is already being used. Please choose another name.");
			}
		} 
	}
	
	@EventHandler() 
	private void changeMatchDurationMinute(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof MatchDurationMinutesIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.updateMinutes(e.getNewInput());
			} else {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				if(e.getNewInput() != 0) {
					((HoverIcon) e.getIcon()).clear();
				}
			}
		}
	}
	
	@EventHandler() 
	private void changeMatchDurationSecond(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof MatchDurationSecondsIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.updateSeconds(e.getNewInput());
			} else {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				if(e.getNewInput() != 0) {
					((HoverIcon) e.getIcon()).clear();
				}
			}
		}
	}
	
	@EventHandler() 
	private void changeMatchDurationTick(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof MatchDurationTicksIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.updateTicks(e.getNewInput());
			} else {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				if(e.getNewInput() != 0) {
					((HoverIcon) e.getIcon()).clear();
				}
			}
		}
	}

	@EventHandler()
	private void changeTeamSize(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetTeamSizeIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = currentKitBuilders.get(player.getName());
				if(e.getNewInput() > 1 && e.getNewInput() < 16) {
					kitBuilder.setTeamSize((byte) e.getNewInput());
				} else {
					player.sendMessage(ChatColor.RED + "Failed to set Team size. The Team size must be greater than 1 and less than 16!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Failed to set Team size. You must set a name for the Kit Builder first!");
				if(e.getNewInput() != 0) {
					((HoverIcon) e.getIcon()).clear();
				}
			}
		}
	}


	@EventHandler()
	private void changeNumberOfTeams(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetNumberOfTeamsIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = currentKitBuilders.get(player.getName());
				if(e.getNewInput() > 1 && e.getNewInput() < 16) {
					kitBuilder.setNumberOfTeams((byte) e.getNewInput());
				} else {
					player.sendMessage(ChatColor.RED + "Failed to set number of Teams. The number of teams must be greater than 1 "
							+ "and less than 16!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Failed to set number of Teams. You must set a name for the Kit Builder first!");
				if(e.getNewInput() != 0) {
					((HoverIcon) e.getIcon()).clear();
				}
			}
		}
	}

	@EventHandler()
	private void selectWinCondition(IconMenuClickEvent e) {
		if(e.getIcon() instanceof WinConditionSelectionIcon) {
			String winConditionName = ((WinConditionSelectionIcon) e.getIcon()).getWinConditionName();
			WinCondition winCondition = ((LibraryCore) Codari.getLibrary()).createWinCondition(winConditionName);
			KitBuilder kitBuilder = currentKitBuilders.get(e.getIcon().getPlayerName());
			kitBuilder.setWinConditionName(winConditionName);		
			kitBuilder.selectWinCondition(winCondition);
			e.getIcon().getCombatant().getPlayer().sendMessage(ChatColor.BLUE + "Selected Win Condition is " + winConditionName); //TODO - For Testing
		}
	}
	
	@EventHandler() 
	private void changeWinConditionMinute(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetWinConditionMinuteIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.setWinConditionMinute(e.getNewInput());
			} else {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				if(e.getNewInput() != 0) {
					((HoverIcon) e.getIcon()).clear();
				}
			}
		}
	}
	
	@EventHandler() 
	private void changeWinConditionSecond(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetWinConditionSecondIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.SetWinConditionSecond(e.getNewInput());
			} else {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				if(e.getNewInput() != 0) {
					((HoverIcon) e.getIcon()).clear();
				}
			}
		}
	}

	@EventHandler() 
	private void changeWinConditionTick(IconHoverUpdateEvent e) {
		if(e.getIcon() instanceof SetWinConditionTickIcon) {
			Player player = e.getIcon().getCombatant().getPlayer();
			if(currentKitBuilders.containsKey(player.getName())) {
				KitBuilder kitBuilder = KitBuilderListener.currentKitBuilders.get(player.getName());
				kitBuilder.SetWinConditionTick(e.getNewInput());
			} else {
				player.sendMessage(ChatColor.RED + "Failed to change Win Condition time. You must set a name for the Kit Builder first!");
				if(e.getNewInput() != 0) {
					((HoverIcon) e.getIcon()).clear();
				}
			}
		}
	}	
}
